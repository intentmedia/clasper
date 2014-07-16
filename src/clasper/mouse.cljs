(ns clasper.mouse
  (:require [clasper.casper :refer [*casper*]]))

;(def ^{:dynamic true}
;  *mouse* (.mouse *casper*))

(def ^{:dynamic true}
  *mouse* (.create (js/require "mouse") *casper*))

(defn click
  "Performs a click on the first element found matching the provided selector expression
  or at given coordinates if two numbers are passed"
  ([str-selector] (.click *mouse* str-selector))
  ([x y] (.click *mouse* x y)))

(defn doubleclick
  "Sends a doubleclick mouse event onto the element matching the provided arguments"
  ([str-selector] (.doubleclick *mouse* str-selector))
  ([x y] (.doubleclick *mouse* x y)))

(defn down
  "Sends a mousedown mouse event onto the element matching the provided arguments"
  ([str-selector] (.down *mouse* str-selector))
  ([x y] (.down *mouse* x y)))

(defn move
  "Moves the mouse cursor onto the element matching the provided arguments"
  ([str-selector] (.move *mouse* str-selector))
  ([x y] (.move *mouse* x y)))

(defn up
  "Sends a mouseup mouse event onto the element matching the provided arguments"
  ([str-selector] (.up *mouse* str-selector))
  ([x y] (.up *mouse* x y)))
