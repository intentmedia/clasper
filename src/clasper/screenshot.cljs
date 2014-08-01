(ns clasper.screenshot
  (:require [clasper.casper :as casper]
            [clasper.tester :as tester]))

(def SCREENSHOT-DIR "screenshots")

(defn capture-selector [filename selector padding]
      "Not implemented")

(defn capture-selectors [selectors padding]
      "not implemented")

(defn capture-page [filename]
      (casper/capture (str SCREENSHOT-DIR "/" filename)))

(defn rescue-screenshot [type] "not implemented")
