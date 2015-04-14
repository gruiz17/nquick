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

(def command-set #{"purge" "read" "write" "names" "help"})

(def cli-options
  [["-n" "--name NAME" "note name"
    :required "note name"
    :default "default"]])

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
          (if (contains? #{"read" "purge"} maincom)
            (if (not (empty? (rest args)))
              (if (= "-n" (first (rest args)))
                ((commands/command-map maincom) (((parse-opts (rest args) cli-options) :options) :name))
                (println "command not recognized."))
              ((commands/command-map maincom)))
            (if (= "write" maincom)
              ((commands/command-map maincom) (first (rest args)))
              ((commands/command-map maincom)))))))))
