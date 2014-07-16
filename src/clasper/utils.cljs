(ns clasper.utils)

(def *utils* (js/require "utils"))

(defn better-type-of
  "Provides a better typeof operator equivalent, eg. able to retrieve the Array type."
  [input] (.betterTypeOf *utils* input))

(defn better-instance-of
  "Provides a better instanceof operator equivalent, is able to retrieve the Array instance or to deal with inheritance."
  [input constructor] (.betterInstanceOf *utils* input constructor))

(defn dump
  "Dumps a JSON representation of passed argument to the standard output. Useful for debugging your scripts."
  [value] (.dump *utils* value))

(defn file-ext
  "Retrieves the extension of passed filename."
  [file] (.fileExt *utils* file))

(defn fill-blanks
  "Fills a string with trailing spaces to match pad length."
  [text pad] (.fillBlanks *utils* text pad))

(defn format
  "Formats a string against passed args. sprintf equivalent."
  [f] (.format *utils* f))

(defn get-property-path
  "Retrieves the value of an Object foreign property using a dot-separated path string"
  [obj path] (.getPropertyPath *utils* obj path))

(defn inherits
  "Makes a constructor inheriting from another. Useful for subclassing and extending."
  [ctor super-ctor] (.inherits *utils* ctor super-ctor))

(defn is-array
  "Checks if passed argument is an instance of Array."
  [value] (.isArray *utils* value))

(defn is-casper-object?
  "Checks if passed argument is an instance of Casper."
  [value] (.isCasperObject *utils* value))

(defn is-clip-rect?
  "Checks if passed argument is a cliprect object."
  [value] (.isClipRect *utils* value))

(defn is-falsy?
  "Returns subject falsiness."
  [value] (.isFalsy *utils* value))

(defn is-function?
  "Checks if passed argument is a function"
  [value] (.isFunction *utils* value))

(defn is-js-file?
  "Checks if passed filename is a Javascript one (by checking if it has a .js or .coffee file extension)."
  [file] (.isJsFile *utils* file))

(defn is-null?
  "Checks if passed argument is a null."
  [value] (.isNull *utils* value))

(defn is-number?
  "Checks if passed argument is an instance of Number."
  [value] (.isNumber *utils* value))

(defn is-object?
  "Checks if passed argument is an object."
  [value] (.isObject *utils* value))

(defn is-string?
  "Checks if passed argument is an instance of String."
  [value] (.isString *utils* value))

(defn is-truthy?
  "Signature: isTruthy(subject)"
  [subject] (.isTruthy *utils* subject))

(defn is-type?
  "Checks if passed argument has its type matching the type argument."
  [what type] (.isType *utils* what type))

(defn is-undefined?
  "Checks if passed argument is undefined."
  [value] (.isUndefined *utils* value))

(defn is-web-page?
  "Checks if passed argument is an instance of native PhantomJS’ WebPage object."
  [what] (.isWebPage *utils* what))

(defn merge-objects
  "Merges two objects recursively. Add opts.keepReferences if cloning of internal objects is not needed."
  ([origin add] (.mergeObjects *utils* origin add))
  ([origin add opts] (.mergeObjects *utils* origin add opts)))

(defn node
  "Creates an (HT|X)ML element, having optional attributes added."
  [name attributes] (.node *utils* name attributes))

(defn serialize
  "Serializes a value using JSON format. Will serialize functions as strings. Useful for debugging and comparing objects."
  [value] (.serialize *utils* value))

(defn unique
  "Retrieves unique values from within a given Array."
  [array] (.unique *utils* array))
