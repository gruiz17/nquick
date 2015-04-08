(ns nquick.core
  (:require [nquick.util :as util]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(defn foo
  "I don't do a whole lot."
  []
  (println util/home-directory))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :default 80
    :required "port number"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "must be number between 0 and 65536"]]
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ["-h" "--help" "lol"]
   ["-f" "--file FILENAME" "Name of File"
    :id :filename
    :default util/nquick-default-file
    :validate [#(nil? (re-find #"\s+" %)) "filename can't have whitespace"]]])

(defn -main [& args]
  (do
      (println (first args))
	  (println (parse-opts (rest args) cli-options))))
