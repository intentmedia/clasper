(ns clasper.assert
  (:require [clasper.casper :as casper]
            [clasper.tester :as tester]))

(def DELAY_BETWEEN_DESCRIBE 5000)

(defn make-verbose []
  (do
    (casper/on "url.changed" (fn [url] (tester/comment (str "URL Changed To: " url))))
    (casper/on "popup.created" (fn [page] (tester/comment (str "Popup Created: " (.-url page)))))
    (casper/on "popup.created" (fn [page] (tester/comment (str "Popup Closed: " (.-url page)))))
    (casper/on "step.start" (fn [step] (tester/comment "Step Started")))
    (casper/on "step.timeout" (fn [step] (tester/comment "Step Timeout")))))

(defn describe [description f]
  (casper/then (fn [] (casper/wait DELAY_BETWEEN_DESCRIBE
                                   (fn [] (do (tester/comment description)
                                              (casper/then f)))))))

(defn on-navigation-request-for [url-regexp callback]
  (let [remove-nav-listener (fn [] ())
        nav-listener (fn [url] (if (.match url url-regexp)
                                 (do (remove-nav-listener) (callback))))
        remove-nav-listener (fn [] (casper/remove-listener "navigation.requested" nav-listener))]
    (do (casper/on "navigation.requested" nav-listener)
      (casper/on "test.abort" remove-nav-listener))))

(defn on-resource-request-for [request-regexp callback]
  (let [remove-resource-listener (fn [] ())
        resource-listener (fn [request] (if (.match (.-url request) request-regexp)
                                          (do (remove-resource-listener)
                                            (callback))))
        remove-resource-listener (fn [] (casper/remove-listener "resource.requested" resource-listener))]
    (do (casper/on "resource.requested" resource-listener)
      (casper/on "test.abort" remove-resource-listener))))

(defn open-and-assert-resource-requested [url request-regexp]
  (binding [test-status (atom :fail)
            pass-test (fn [] (tester/assert true "Matching request made."))
            fail-test (fn [] (tester/assert false "No matching request made."))]
    (do (on-resource-request-for request-regexp (fn [] (reset! test-status :pass)))
      (casper/open url)
      (casper/wait-for (fn [] (identical? test-status :pass)) pass-test fail-test))))
