(def last-describe (atom ""))
(def screenshot-dir "")

(def fs (js/require "fs"))
(def utils (js/require "utils"))


(defn dump-to-html-file [typ]
  (let [timestamp (.getTime (new js/Date))
        html (casper/evaluate (fn [] (.-innerHTML (.-documentElement js/document))))
        filename (str "casper-" typ "-" timestamp "-" (.replace last-describe (js/Regexp. "/\W/g") "") ".html")
        f (.open fs (str screenshot-dir "/" filename) "w")]
    (do (.write f html)
        (.close f))))
