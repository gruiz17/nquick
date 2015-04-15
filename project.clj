(defproject nquick "0.0.2.1"
  :description "A very simple note taking app"
  :url "http://github.com/gruiz17/nquick"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]]
  :main nquick.core
  
  :bin {:name "nq"
	    :bin-path "/usr/local/bin"
	    :bootclasspath true})
