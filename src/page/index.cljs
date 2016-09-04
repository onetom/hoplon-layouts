(ns ^{:hoplon/page "index.html"} page.index
  (:require
    [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]
    [hoplon.core :as h :refer [defelem when-tpl if-tpl case-tpl for-tpl]]
    [devtools.core]))

(defonce
  _first_load_
  (do
    (devtools.core/install! [:custom-formatters :hints :async])))

(defn reload []
  (js/console.log "Reload callback was called"))

(h/html
  (h/body
    (h/h1 "Reloadable page")))
