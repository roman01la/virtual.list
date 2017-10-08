(defproject org.roman01la/virtual.list "0.1.0"
  :description "Virtual list component for Prum"
  :url "https://github.com/roman01la/virtual-list"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0" :scope "provided"]
                 [org.clojure/clojurescript "1.9.908" :scope "provided"]
                 [org.roman01la/prum "0.10.8-5" :scope "provided"]
                 [org.roman01la/sablono "0.8.1-9" :scope "provided"]]

  :profiles {:dev {:source-paths ["examples"]
                   :dependencies [[org.roman01la/prum "0.10.8-5"]]}}

  :plugins [[lein-cljsbuild "1.1.7"]]

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src" "examples"]
     :compiler
                   {:main           example.core
                    :output-dir     "target/out"
                    :output-to      "target/main.js"
                    :optimizations  :none
                    :pretty-print   false
                    :compiler-stats true
                    :parallel-build true
                    :install-deps   true}}
    {:id           "min"
     :source-paths ["src" "examples"]
     :compiler
                   {:main           example.core
                    :output-to      "target/main.js"
                    :optimizations  :advanced
                    :source-map     "target/main.js.map"
                    :pretty-print   false
                    :compiler-stats true
                    :parallel-build true
                    :install-deps   true}}]})
