(ns clasper.assert
  (:require [clasper.casper :as casper]
            [clasper.tester :as tester]))

(def DELAY_BETWEEN_DESCRIBE 5000)

(defn assert-selectors [selectors]
      (doall (map (fn [selector] (tester/assert-selector-exists selector (str selector " should exist on the page"))) selectors)))

(defn assert-cookies [cookies] nil)

(defn assert-meta-tag [expected-tag-value tag-name])

(defn assert-meta-tags [tags]
      (doall (map (fn [tag] (assert-meta-tag tag)) tags)))

(defn make-verbose []
      (do
        (casper/on "url.changed" (fn [url] (tester/comment (str "URL Changed To: " url))))
        (casper/on "popup.created" (fn [page] (tester/comment (str "Popup Created: " (.-url page)))))
        (casper/on "popup.created" (fn [page] (tester/comment (str "Popup Closed: " (.-url page)))))
        (casper/on "step.start" (fn [step] (tester/comment "Step Started")))
        (casper/on "step.timeout" (fn [step] (tester/comment "Step Timeout")))))

(defn describe [description f]
      (casper/then (fn []
                       (casper/wait DELAY_BETWEEN_DESCRIBE
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
            resource-listener (fn [request]
                                  (let [url (.-url request)]
                                       (if (re-matches request-regexp url)
                                         (do (remove-resource-listener)
                                             (callback)))))
            remove-resource-listener (fn [] (casper/remove-listener "resource.requested" resource-listener))]
           (do (casper/on "resource.requested" resource-listener)
               (casper/on "test.abort" remove-resource-listener))))

(defn open-and-assert-resource-requested [url request-regexp]
      (let [test-passed (atom false)
            pass-test (fn [] (tester/assert true "Matching request made."))
            fail-test (fn [] (tester/assert false "No matching request made."))]
           (do (on-resource-request-for request-regexp (fn [] (reset! test-passed true)))
               (casper/open url)
               (casper/wait-for (fn [] (= @test-passed true)) pass-test fail-test))))

(defn assert-iframe-requests-regex-after-step [iframe-regex request-regex callback]
      (let [iframe (atom "")]
           (do (on-navigation-request-for iframe-regex (fn [iframe-url] (reset! iframe iframe-url)))
               (callback)
               (casper/wait-for (fn [] (> (count @iframe) 0))
                                (fn [] (open-and-assert-resource-requested @iframe request-regex))))))
