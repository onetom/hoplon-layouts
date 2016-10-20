(require '[boot.core :refer :all]                           ; IntelliJ "integration"
         '[boot.task.built-in :refer :all])

(task-options!
  pom {:project     'hoplon-layouts
       :version     "1.0"
       :description "Hoplon webapp layouts"
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(set-env!
  :dependencies
  '[[org.clojure/clojure "1.9.0-alpha13" :scope "provided"]
    [org.clojure/clojurescript "1.9.229" :scope "compile"]

    [boot/core "2.6.0" :scope "provided"]
    [onetom/boot-lein-generate "0.1.3" :scope "test"]
    [adzerk/boot-cljs "1.7.228-1" :scope "compile"]
    [adzerk/boot-reload "0.4.12" :scope "compile"]
    [hoplon/boot-hoplon "0.2.5" :scope "compile"]
    [tailrecursion/boot-static "0.1.0"]

    [binaryage/devtools "0.8.2" :scope "test"]
    [binaryage/dirac "0.7.2" :scope "test"]
    [powerlaces/boot-cljs-devtools "0.1.2" :scope "test"]

    [hoplon "6.0.0-alpha16" :scope "compile"]]
  :source-paths #{"src"}
  :asset-paths #{"assets"})

(require '[boot.lein])
(boot.lein/generate)

(require
  '[adzerk.boot-reload :refer [reload]]
  '[hoplon.boot-hoplon :refer [hoplon prerender]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[tailrecursion.boot-static :refer [serve]]
  '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]])

(def devtools-config
  {:features-to-install           [:formatters :hints :async]
   :dont-detect-custom-formatters true})

(deftask dev []
  (comp
    (watch)
    (checkout :dependencies '[[hoplon "6.0.0-alpha16"]])
    (hoplon)
    (reload :on-jsload 'page.index/reload)
    (cljs-devtools)
    (cljs :compiler-options {:parallel-build  true
                             :external-config {:devtools/config devtools-config}})
    (serve :port 3000)))
