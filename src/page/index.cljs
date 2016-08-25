(ns ^{:hoplon/page "index.html"} page.index
  (:require
    [hoplon.core :as h
     :refer-macros [defelem text with-init! with-page-load
                    with-timeout with-interval
                    when-tpl if-tpl case-tpl cond-tpl for-tpl loop-tpl]]
    [javelin.core
     :refer [cell? input? cell formula lens cell-map
             set-cell! alts! destroy-cell!]
     :refer-macros [cell= defc defc= set-cell!= dosync cell-doseq
                    with-let cell-let]]
    [devtools.core]))

(devtools.core/install! [:custom-formatters :hints :async])

(defn reload [] (js/console.log "Reload callback was called"))

(h/html
  (h/body
    (h/h1 "Reloadable page")))
