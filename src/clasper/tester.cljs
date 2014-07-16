(ns clasper.tester
  (:require [clasper.casper :refer [*casper* ->js]])
  (:refer-clojure :exclude [repeat comment assert]))

(def ^{:doc "Default tester instance"
       :dynamic true}
  *test* (.create (js/require "tester") *casper*))

;; Tester API
(defn abort
  ([] (.abort *test*))
  ([message] (.abort *test* message)))

(defn skip [num-tests message] (.skip *test* num-tests message))

(defn assert-true
  "Asserts that the provided condition strictly resolves to a boolean true"
  ([subject message] (.assert *test* subject message))
  ([subject message context] (.assert *test* subject message context)))
(def assert assert-true)

(defn assert-equals
  "Asserts that two values are strictly equivalent"
  ([subject expected] (.assertEquals *test* subject expected))
  ([subject expected message] (.assertEquals *test* subject expected message)))

(defn assert-not-equals
  "Asserts that two values are not strictly equals"
  ([subject shouldnt] (.assertNotEquals *test* subject shouldnt))
  ([subject shouldnt message] (.assertNotEquals *test* subject shouldnt message)))

(defn assert-element-count
  "Asserts that a selector expression matches a given number of elements"
  ([sel cnt] (.assertElementCount *test* (name sel) cnt))
  ([sel cnt message] (.assertElementCount *test* (name sel) cnt message)))

(defn assert-evaluate
  "Asserts that a code evaluation in remote DOM strictly resolves to a boolean true"
  ([f message] (.assertEvaluate *test* f message))
  ([f message params] (.assertEvaluate *test* f message params)))
(def assert-eval assert-evaluate)

(defn assert-eval-equals
  "Asserts that the result of a code evaluation in remote DOM strictly equals to the expected value"
  ([f expected message] (.assertEvalEquals *test* f message))
  ([f expected message params] (.assertEvalEquals *test* f message params)))
(def assert-eval-equal assert-eval-equals)

(defn assert-field
  "Asserts that a given form field has the provided value with input name or selector expression."
  ([input expected message] (.assertField *test* input expected message))
  ([input expected message options] (.assertField *test* input expected message options)))

(defn assert-field-name
  [] (print "not supported yet"))

(defn assert-field-css
  "Asserts that a given form field has the provided value given a CSS selector"
  [css-selector expected message] (.assertFieldCSS *test* css-selector expected message))

(defn assert-field-xpath
  "Asserts that a given form field has the provided value given a XPath selector"
  [xpath expected message] (.assertFieldXPath *test* xpath expected message))

(defn assert-exists
  "Asserts that an element matching the provided selector expression exists in remote DOM environment."
  ([sel] (.assertExists *test* (name sel)))
  ([sel message] (.assertExists *test* (name sel) message)))
(def assert-exist assert-exists)
(def assert-selector-exist assert-exists)
(def assert-selector-exists assert-exists)

(defn assert-doesnt-exists
  "Asserts that an element matching the provided selector expression doesn’t exists within the remote DOM environment"
  ([sel] (.assertDoesntExists *test* (name sel)))
  ([sel message] (.assertDoesntExists *test* (name sel) message)))
(def assert-doesnt-exist assert-doesnt-exists)

(defn assert-http-status
  "Asserts that current HTTP status code is the same as the one passed as argument"
  ([status] (.assertHttpStatus *test* status))
  ([status message] (.assertHttpStatus *test* status message)))

(defn assert-match
  "Asserts that a provided string matches a provided javascript RegExp pattern"
  ([subject pattern] (.assertMatch *test* subject pattern))
  ([subject pattern message] (.assertMatch *test* subject pattern message)))
(def assert-matches assert-match)

(defn assert-false
  "Asserts that the passed subject resolves to some falsy value."
  ([condition] (.assertFalse *test* condition))
  ([condition message] (.assertFalse *test* condition message)))
(def assert-not assert-false)

(defn assert-not-visible
  "Asserts that the element matching the provided selector expression is not visible"
  ([expected] (.assertNotVisible *test* expected))
  ([expected message] (.assertNotVisible *test* expected message)))
(def assert-invisible assert-not-visible)

(defn assert-raises
  "Asserts that the provided function called with the given parameters raises a javascript Error"
  ([f args] (.assertRaises *test* f args))
  ([f args message] (.assertRaises *test* f args message)))
(def assert-raise assert-raises)
(def assert-throws assert-raises)

(defn assert-resource-exists
  "The testFx function is executed against all loaded assets and the test passes when at least one resource matches"
  ([f] (.assertResourceExists *test* f))
  ([f message] (.assertResourceExists *test* f message)))
(def assert-resource-exist assert-resource-exists)

(defn assert-text-doesnt-exist
  "Asserts that body plain text content doesn’t contain the given string"
  ([text] (.assertTextDoesntExist *test* text))
  ([text message] (.assertTextDoesntExist *test* text message)))

(defn assert-text-exists
  "Asserts that body plain text content contains the given string"
  ([text] (.assertTextExists *test* text))
  ([text message] (.assertTextExists *test* text message)))
(def assert-text-exist assert-text-exists)

(defn assert-truthy
  "Asserts that a given subject is truthy."
  ([subject] (.assertTruthy *test* subject))
  ([subject message] (.assertTruthy *test* subject message)))

(defn assert-falsy
  "Asserts that a given subject is falsy"
  ([subject] (.assertFalsy *test* subject))
  ([subject message] (.assertFalsy *test* subject message)))

(defn assert-selector-has-text
  "Asserts that given text exists in elements matching the provided selector expression"
  ([sel text] (.assertSelectorHasText *test* (name sel) text))
  ([sel text message] (.assertSelectorHasText *test* (name sel) text message)))
(def assert-selector-contains assert-selector-has-text)

(defn assert-selector-doesnt-have-text
  "Asserts that given text does not exist in all the elements matching the provided selector expression"
  ([sel text] (.assertSelectorDoesntHaveText *test* (name sel) text))
  ([sel text message] (.assertSelectorDoesntHaveText *test* (name sel) text message)))
(def assert-selector-doesnt-contain assert-selector-doesnt-have-text)

(defn assert-title
  "Asserts that title of the remote page equals to the expected one."
  ([expected] (.assertTitle *test* expected))
  ([expected message] (.assertTitle *test* expected message)))

(defn assert-title-match
  "Asserts that title of the remote page matches the provided RegExp pattern."
  ([pattern] (.assertTitleMatch *test* pattern))
  ([pattern message] (.assertTitleMatch *test* pattern message)))
(def assert-title-matches assert-title-match)

(defn assert-type
  "Asserts that the provided input is of the given type."
  ([subject typ] (.assertType *test* subject typ))
  ([subject typ message] (.assertType *test* subject typ message)))

(defn assert-instance-of
  "Asserts that the provided input is of the given constructor."
  ([subject constructor] (.assertInstanceOf *test* subject constructor))
  ([subject constructor message] (.assertInstanceOf *test* subject constructor message)))

(defn assert-url-match
  "Asserts that the current page url matches the provided RegExp pattern"
  ([pattern] (.assertUrlMatch *test* pattern))
  ([pattern message] (.assertUrlMatch *test* pattern message)))
(def assert-url-matches assert-url-match)

(defn assert-visible
  "Asserts that the element matching the provided selector expression is visible."
  ([sel] (.assertVisible *test* (name sel)))
  ([sel message] (.assertVisible *test* (name sel) message)))

(defn bar [text style] (.bar *test* text style))

(defn set-up [f] (.setUp *test* f))

(defn tear-down [f] (.tearDown *test* f))

(defn begin
  ([description f] (.begin *test* description f))
  ([description number f] (.begin *test* description number f)))

(defn comment [message] (.comment *test* message))

(defn done [] (.done *test*))

(defn error [message] (.error *test* message))

(defn exec [file] (.exec *test* file))

(defn info [message] (.info *test* message))

(defn run-suites [] (.runSuites *test*))

(defn run-test [test-file] (.runTest *test* test-file))

(defn calculate-duration [] (.calculateDuration *test*))

(defn colorize
  "Render a colorized output."
  [message style] (.colorize *test* message style))

(defn pass
  "Adds a successful test entry to the stack."
  [message] (.pass *test* message))

(defn fail
  "Adds a failed test entry to the stack"
  [message] (.fail *test* message))

(defn render-results
  "Render test results, save results in an XUnit formatted file, and optionally exits phantomjs"
  [exit status save] (.renderResults *test* exit status save))

(defn skip
  "Skips a given number of planned tests"
  [number message] (.skip *test* number message))
