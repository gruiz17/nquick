(ns nquick.core
  (:require [nquick.util :as util]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(defn foo
  "I don't do a whole lot."
  []
  (println util/home-directory))

(defn -main 
  ([]
   (do
     (util/check-dir)
     (println (slurp util/nquick-default-file))))
  ([& args]
   	(do
      (util/check-dir)
      (if (= 1 (count args))
        (if (= "-flush" (first args))
          (util/destroy-and-recreate)
          (spit util/nquick-default-file (str (first args) "\n") :append true))
      	(spit util/nquick-default-file (str (string/join " " args) "\n") :append true)))))
