(ns nquick.core
  (:require [nquick.util :as util]
            [nquick.commands :as commands]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(defn foo
  "I don't do a whole lot."
  []
  (println util/home-directory))

(def command-set #{"purge" "readnote" "write" "names" "help"})

(defn -main 
  ([]
   (commands/help))
  ([& args]
   (do
      (util/check-dir)
      (util/check-default-file)
      )))
