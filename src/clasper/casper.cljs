(ns clasper.casper
  (:refer-clojure :exclude [repeat]))

;; Utilities
(defn ->js [x]
  (cond (string? x) x
    (keyword? x) (name x)
    (map? x) (.-strobj (reduce (fn [m [k v]] (assoc m (->js k) (->js v))) {} x))
    (coll? x) (apply array (map ->js x))
    :else x))

(def ^{:doc "Default casper instance, can be overridden by using set-casper-options!"
       :dynamic true}
  *casper* (.create (js/require "casper")))

(defn set-casper-options!
  "Redefine *casper* with specific options: http://casperjs.org/api.html#casper.options"
  [opts]
  (set! *casper* (.create (js/require "casper") (->js opts))))

;; Casper events
(defn on
      "set up an event"
      [event f] (.on *casper* event f))
(def add-listener on)

(defn once
  "Do a listener once"
  [event f] (.once *casper* event f))

(defn remove-listener
  "Remove a listener event"
  [event f] (.removeListener *casper* event f))

(defn remove-all-listeners
  "Remove all listeners of a type"
  [event] (.removeAllListeners *casper* event))

(defn emit
  "Emit an event"
  [event] (.emit *casper* event))

;; Casper filters
(defn set-filter
  "Set up a filter"
  [event f] (.setFilter *casper* event f))

(defn remove-all-filters
  [type] (.removeAllFilters *casper* type))

;; Casper API
(defn back
  [] (.back *casper*))

(defn base64-encode
  ([s] (.base64encode *casper* s))
  ([s method data] (.base64encode *casper* s (name method) (->js data))))

(defn click
  [sel] (.click *casper* (name sel)))

(defn click-label
  "Clicks on the first DOM element found containing label text. Optionaly ensures that the element node name is tag"
  ([s] (.clickLabel *casper* s))
  ([s tag] (.clickLabel *casper* s (name tag))))

(defn capture
  "Proxy method for PhantomJS’ WebPage#render.
  Adds a clipRect parameter for automatically setting page clipRect setting and reverts it back once done"
  [path rect] (.capture *casper* path (->js rect)))

(defn capture-base64
  "Computes the Base64 representation of a binary image capture of the current page,
      or an area within the page, in a given format.

      Supported image formats are bmp, jpg, jpeg, png, ppm, tiff, xbm and xpm.

      The area argument can be either of the following types:
      String: area is a CSS3 selector string
      clipRect: area is a clipRect object
      Object: area is a selector object"
  ([fmt] (.captureBase64 *casper* (name fmt)))
  ([fmt part] (.captureBase64 *casper* (name fmt) (if (string? part) part (->js part)))))

(defn capture-selector
  "Captures the page area containing the provided selector and saves it to targetFile"
  [out sel] (.captureSelector *casper* out (name sel)))

(defn clear
  "Clears the current page execution environment context.
      Useful to avoid having previously loaded DOM contents being still active.
      Think of it as a way to stop javascript execution within the remote DOM environment"
  [] (.clear *casper*))

(defn debug-html
  "Outputs the results of getHTML() directly to the console. It takes the same arguments as getHTML()"
  [] (.debugHTML *casper*))

(defn debug-page
  "Logs the textual contents of the current page directly to the standard output, for debugging purpose"
  [] (.debugPage *casper*))

(defn die
  "Exits phantom with a logged error message and an optional exit status code"
  ([s] (.die *casper* s))
  ([s status] (.die *casper* s status)))

(defn download
  "Saves a remote resource onto the filesystem.
    You can optionally set the HTTP method using the method argument, and pass request arguments through the data object"
  ([url out] (.download *casper* url out))
  ([url out method data] (.download *casper* url out (name method) (->js data))))

(defn each
  "Iterates over provided array items and execute a callback"
  [array callback] (.each *casper* array callback))

(defn each-then
  "Iterates over provided array items and adds a step to the stack with current data attached to it"
  [array then] (.eachThen *casper* array then))

(defn echo
  "Prints something to stdout, optionally with some fancy color (see the colorizer module for more information)"
  ([message] (.echo *casper* message))
  ([message style] (.echo *casper* message style)))

(defn evaluate
  "Basically PhantomJS’ WebPage#evaluate equivalent. Evaluates an expression in the current page DOM context"
  ([f] (.evaluate *casper* f))
  ([f ctx] (.evaluate *casper* f (->js ctx))))

(defn evaluate-or-die
  "Evaluates an expression within the current page DOM and die() if it returns anything but true"
  ([f] (.evaluateOrDie *casper* f))
  ([f message] (.evaluateOrDie *casper* f message)))

(defn exit
  "Exits PhantomJS with an optional exit status code."
  ([] (.exit *casper*))
  ([status] (.exit *casper* status)))

(defn exists?
  "Checks if any element within remote DOM matches the provided selector"
  [sel] (.exists *casper* (name sel)))

(defn fetch-text
  "Retrieves text contents matching a given selector expression.
  If you provide one matching more than one element, their textual contents will be concatenated"
  [sel] (.fetchText *casper* (name sel)))

(defn forward
  "Moves a step forward in browser’s history"
  [] (.forward *casper*))

(defn log
  "Logs a message with an optional level in an optional space"
  ([s] (.log *casper* s))
  ([s level] (.log *casper* s (name level)))
  ([s level space] (.log *casper* s (name level) space)))

(defn fill
  "Fills the fields of a form with given values and optionally submits it"
  ([sel data] (.fill *casper* (name sel) (->js data)))
  ([sel data submit?] (.fill *casper* (name sel) (->js data) submit?)))

(defn fill-selectors
  "Fills form fields with given values and optionally submits it. Fields are referenced by CSS3 selectors"
  ([sel values] (.fillSelectors *casper* (name sel) values))
  ([sel values submit] (.fillSelectors *casper* (name sel) values submit)))

(defn fill-xpath
  "Fills form fields with given values and optionally submits it.
  While the form element is always referenced by a CSS3 selector, fields are referenced by XPath selectors"
  ([sel values] (.fillXPath *casper* (name sel) values))
  ([sel values submit] (.fillXPath *casper* (name sel) values submit)))

(defn get-current-url
  "Retrieves current page URL. Note that the url will be url-decoded"
  [] (.getCurrentUrl *casper*))

(defn get-element-attribute
  "Retrieves the value of an attribute on the first element matching the provided selector"
  [sel attr] (.getElementAttribute *casper* (name sel) (name attr)))

(defn get-element-bounds
  "Retrieves boundaries for a DOM element matching the provided selector"
  [sel] (js->clj (.getElementBounds *casper* (name sel)) :keywordize-keys true))

(defn get-elements-bounds
  "Retrieves a list of boundaries for all DOM elements matching the provided selector"
  [sel] (js->clj (.getElementsBounds *casper* (name sel)) :keywordize-keys true))

(defn get-element-info
  "Retrieves information about the first element matching the provided selector"
  [sel] (js->clj (.getElementInfo *casper* (name sel)) :keywordize-keys true))

(defn get-elements-info
  "Retrieves information about all elements matching the provided selector"
  [sel] (js->clj (.getElementsInfo *casper* (name sel)) :keywordize-keys true))

(defn get-form-values
  "Retrieves a given form all of its field values"
  [sel] (.getFormValues *casper* (name sel)))

(defn get-global
  "Retrieves a global variable value within the remote DOM environment by its name"
  [name] (.getGlobal *casper* name))

(defn get-html
  "Retrieves HTML code from the current page"
  ([] (.getHTML *casper*))
  ([sel] (.getHTML *casper*))
  ([sel outer] (.getHTML *casper*)))

(defn get-page-content
  "Retrieves current page contents, dealing with exotic other content types than HTML"
  [] (.getPageContent *casper*))

(defn get-title
  "Retrieves current page title"
  [] (.getTitle *casper*))

(defn mouse-event "Triggers a mouse event on the first element found matching the provided selector"
  [type sel] (.mouseEvent *casper* (name type) (name sel)))

(defn open
  "Performs an HTTP request for opening a given location"
  ([url] (.open *casper* url))
  ([url opts] (.open *casper* url (->js opts))))

(defn reload
  "Reloads current page location"
  [f] (.reload *casper* f))

(defn repeat
  "Repeats a navigation step a given number of times"
  [n f] (.repeat *casper* n f))

(defn resource-exists?
  "Checks if a resource has been loaded. You can pass either a function, a string or a RegExp instance to perform the test."
  [url] (.resourceExists *casper* url))

(defn run
  "Runs the whole suite of steps and optionally executes a callback when they’ve all been done.
  Obviously, calling this method is mandatory in order to run the Casper navigation suite."
  ([] (.run *casper*))
  ([f] (.run *casper* f)))

(defn scroll-to
  "Scrolls current document to the coordinates defined by the value of x and y"
  [x y] (.scrollTo *casper* x y))

(defn scroll-to-bottom
  "Scrolls current document to its bottom."
  [] (.scrollToBottom *casper*))

(defn send-keys
  "Sends native keyboard events to the element matching the provided selector."
  ([sel keys] (.sendKeys *casper* (name sel) keys))
  ([sel keys opts] (.sendKeys *casper* (name sel) keys opts)))

(defn set-http-auth
  "Sets HTTP_AUTH_USER and HTTP_AUTH_PW values for HTTP based authentication systems"
  [username password] (.setHttpAuth *casper* username password))

(defn start
  "Configures and starts Casper, then open the provided url and optionally adds the step provided by the then argument."
  ([url] (.start *casper* url))
  ([url f] (.start *casper* url f)))

(defn status
  "Returns the status of current Casper instance"
  [as-string] (.status *casper* as-string))

(defn then
  "This method is the standard way to add a new navigation step to the stack"
  [f] (.then *casper* f))

(defn then-bypass
  "Adds a navigation step which will bypass a given number of following steps"
  [num] (.thenBypass *casper* num))

(defn then-bypass-if
  "Bypass a given number of navigation steps if the provided condition is truthy or is a function that returns a truthy value"
  [condition num] (.thenBypassIf *casper* condition num))

(defn then-bypass-unless
  [condition num] (.thenBypassUnless *casper* condition num))

;thenClick

(defn then-evaluate
  "Adds a new navigation step to perform code evaluation within the current retrieved page DOM"
  ([f] (.thenEvaluate *casper* f))
  ([f ctx] (.thenEvaluate *casper* f (->js ctx))))

(defn then-open
  "Adds a new navigation step for opening a new location, and optionally add a next step when its loaded"
  ([url] (.thenOpen *casper* url))
  ([url f] (.thenOpen *casper* url f))
  ([url opts f] (.thenOpen *casper* url (->js opts) f)))

(defn then-open-and-evaluate
  "Basically a shortcut for opening an url and evaluate code against remote DOM environment"
  ([url] (.thenOpenAndEvaluate *casper* url))
  ([url f] (.thenOpenAndEvaluate *casper* url f))
  ([url f ctx] (.thenOpenAndEvaluate *casper* url f (->js ctx))))

(defn to-string
  "Returns a string representation of current Casper instance"
  [] (.toString *casper*))

(defn unwait
  "Abort all current waiting processes, if any."
  [] (.unwait *casper*))

(defn user-agent
  "Sets the User-Agent string to send through headers when performing requests"
  [s] (.userAgent *casper* s))

(defn viewport
  "Changes current viewport size"
  [w h] (.viewport *casper* w h))

(defn visible?
  "Checks if the DOM element matching the provided selector expression is visible in remote page"
  [sel] (.visible *casper* (name sel)))

(defn wait
  "Pause steps suite execution for a given amount of time, and optionally execute a step on done."
  ([ms] (.wait *casper* ms))
  ([ms f] (.wait *casper* ms f)))

(defn wait-for
  "Waits until a function returns true to process any next step."
  ([pred] (.waitFor *casper* pred))
  ([pred f] (.waitFor *casper* pred f))
  ([pred f on-timeout] (.waitFor *casper* pred f on-timeout))
  ([pred f on-timeout time] (.waitFor *casper* pred f on-timeout time)))

(defn wait-for-alert
  "Waits until a JavaScript alert is triggered.
  The step function will be passed the alert message in the response.data property"
  ([then] (.waitForAlert *casper* then))
  ([then on-timeout] (.waitForAlert *casper* then on-timeout))
  ([then on-timeout time] (.waitForAlert *casper* then on-timeout time)))

(defn wait-for-popup
  "Waits for a popup having its url matching the provided pattern to be opened and loaded."
  ([url] (.waitForPopup *casper* url))
  ([url f] (.waitForPopup *casper* url f))
  ([url f on-timeout] (.waitForPopup *casper* url f on-timeout))
  ([url f on-timeout time] (.waitForPopup *casper* url f on-timeout time)))

(defn wait-for-selector
  "Waits until an element matching the provided selector expression exists in remote DOM to process any next step."
  ([sel] (.waitForSelector *casper* (name sel)))
  ([sel f] (.waitForSelector *casper* (name sel) f))
  ([sel f on-timeout] (.waitForSelector *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitForSelector *casper* (name sel) f on-timeout time)))

(defn wait-while-selector
  "Waits until an element matching the provided selector expression does not exist in remote DOM to process a next step."
  ([sel] (.waitWhileSelector *casper* (name sel)))
  ([sel f] (.waitWhileSelector *casper* (name sel) f))
  ([sel f on-timeout] (.waitWhileSelector *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitWhileSelector *casper* (name sel) f on-timeout time)))

(defn wait-for-selector-text-change
  "Waits until the text on an element matching the provided selector expression
  is changed to a different value before processing the next step."
  ([sel] (.waitForSelectorTextChange *casper* (name sel)))
  ([sel then] (.waitForSelectorTextChange *casper* (name sel) then))
  ([sel then on-timeout] (.waitForSelectorTextChange *casper* (name sel) then on-timeout))
  ([sel then on-timeout time] (.waitForSelectorTextChange *casper* (name sel) then on-timeout time)))

(defn wait-for-text
  "Waits until the passed text is present in the page contents before processing the immediate next step."
  ([text] (.waitForText *casper* text))
  ([text then] (.waitForText *casper* text then))
  ([text then on-timeout] (.waitForText *casper* text then on-timeout))
  ([text then on-timeout time] (.waitForText *casper* text then on-timeout time)))

(defn wait-for-resource
  "Wait until a resource that matches a resource matching constraints defined by testFx are satisfied to process a next step."
  ([url] (.waitForResource *casper* url))
  ([url f] (.waitForResource *casper* url f))
  ([url f on-timeout] (.waitForResource *casper* url f on-timeout))
  ([url f on-timeout time] (.waitForResource *casper* url f on-timeout time)))

(defn wait-for-url
  "Waits for a popup having its url matching the provided pattern to be opened and loaded."
  ([url] (.waitForUrl *casper* url))
  ([url then] (.waitForUrl *casper* url then))
  ([url then on-timeout] (.waitForUrl *casper* url then on-timeout))
  ([url then on-timeout time] (.waitForUrl *casper* url then on-timeout time)))

(defn wait-until-visible
  "Waits until an element matching the provided selector expression is visible in the remote DOM to process a next step. "
  ([sel] (.waitUntilVisible *casper* (name sel)))
  ([sel f] (.waitUntilVisible *casper* (name sel) f))
  ([sel f on-timeout] (.waitUntilVisible *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitUntilVisible *casper* (name sel) f on-timeout time)))

(defn wait-while-visible
  "Waits until an element matching the provided selector expression is no longer visible in remote DOM to process a next step."
  ([sel] (.waitWhileVisible *casper* (name sel)))
  ([sel f] (.waitWhileVisible *casper* (name sel) f))
  ([sel f on-timeout] (.waitWhileVisible *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitWhileVisible *casper* (name sel) f on-timeout time)))

(defn warn
  "Logs and prints a warning message to the standard output"
  [s] (.warn *casper* s))

(defn with-frame
  "Switches the main page to the frame having the name or frame index number matching the passed argument,
  and processes a step."
  [frame-info then] (.withFrame *casper* frame-info then))

(defn with-popup
  "Switches the main page to a popup matching the information passed as argument, and processes a step.
  The page context switch only lasts until the step execution is finished"
  [popup-info then] (.withPopup *casper* popup-info then))

(defn zoom
  "Sets the current page zoom factor"
  [n] (.zoom *casper* n))
