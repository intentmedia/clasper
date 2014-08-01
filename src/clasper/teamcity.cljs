(ns clasper.teamcity
  (:require [clasper.casper :as casper]))

;;todo fill in
(defn IS_TEAMCITY false)

(defn teamcity-encode [message]
  nil)

(defn teamcity-message [name attrs]
  nil)

(defn teamcity-echo [message failed]
  (let [attrs {:name message}]
    (do (teamcity-message "testStarted" attrs)
        (if failed (teamcity-message "testFailed" attrs))
        (teamcity-message "testFinished" attrs))))
