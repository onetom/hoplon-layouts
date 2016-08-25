(require '[boot.core :refer :all]                           ; IntelliJ "integration"
         '[boot.task.built-in :refer :all])

(task-options!
  pom {:project     'boot-reloadable-hoplon
       :version     "1.0"
       :description "Boot reload js-onlode issue"
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(set-env!
  :dependencies `[[org.clojure/clojure "1.8.0" :scope "compile"]
                  [org.clojure/clojurescript "1.9.225" :scope "compile"]
                  [darongmean/boot-lein-generate "0.1.1" :scope "test"]
                  [boot/core ~*boot-version* :scope "compile"]
                  [hoplon/hoplon "6.0.0-alpha16" :scope "compile"]
                  [adzerk/boot-cljs "1.7.228-1" :scope "compile"]
                  [adzerk/boot-reload "0.4.12" :scope "compile"]
                  [hoplon/boot-hoplon "0.2.4" :scope "compile"]
                  [tailrecursion/boot-static "0.0.1-SNAPSHOT"]
                  [binaryage/devtools "0.8.1"]]
  :source-paths #{"src"})

(require '[darongmean.boot-lein-generate :refer [lein-generate]])
(lein-generate)

(require
  '[adzerk.boot-reload :refer [reload]]
  '[hoplon.boot-hoplon :refer [hoplon prerender]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[tailrecursion.boot-static :refer [serve]])

(deftask dev []
  (comp
    (watch)
    (hoplon)
    (reload :on-jsload 'page.index/reload)
    (cljs :compiler-options {:parallel-build true})
    (serve :port 8000)))
