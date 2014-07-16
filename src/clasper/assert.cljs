(ns clasper.assert)
  (:require [clasper.casper :as casper]
            [clasper.tester :as tester])



(defn on-navigation-request-for [url-regexp callback]
  (let [nav-listener (fn [url] (if (.match url url-regexp)
                                 (do (remove-nav-listener) (callback))))
        remove-nav-listener (fn [] (casper/remove-listener "navigation.requested" nav-listener))]
    (do (casper/on "navigation.requested" nav-listener)
        (casper/on "test.abort" remove-nav-listener))))


(defn on-resource-request-for [request-regexp callback]
  (let [resource-listener (fn [request] (if (.match (.url request) request-regexp)
                                          (do (remove-resource-listener) (callback))))
        remove-resource-listener (fn [] (casper/remove-listener "resource.requested" resource-listener))]
    (do (casper/on "resource.requested" resource-listener)
        (casper/on "test.abort" remove-resource-listener))))


(defn open-and-assert-resource-requested [url request-regexp]
  (let [test-status (atom "fail")
        pass-test (fn [] (tester/assert true "Matching request made."))
        fail-test (fn [] (tester/assert false "No matching request made."))]
    (do (on-resource-request-for request-regexp (fn [] (reset! test-status "pass")))
        (casper/open "url")
        (casper/wait-for (fn [] (= test-status "pass")) pass-test fail-test))))
