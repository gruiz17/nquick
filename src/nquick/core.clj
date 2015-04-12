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

(def cli-options
  [["-n" "--name NAME" "note name"
    :required "note name"]])

(defn -main 
  ([]
   (commands/help))
  ([& args]
   (do
      (util/check-dir)
      (util/check-default-file)
      (let [maincom (first args)]
        (if (not (contains? command-set maincom))
          (commands/help)
          (if (contains? #{"readnote" "purge"} maincom)
            (if (not (nil? (rest args)))
              (if (= "-n" (first (rest args)))
                ((commands/fn-map maincom) (((parse-opts rest cli-options) :options) :name))
                (println "command not recognized."))
              (commands/fn-map maincom))
            (commands/fn-map maincom)))))))
