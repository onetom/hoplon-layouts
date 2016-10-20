(ns ^{:hoplon/page "index.html"} page.index
  (:require
    [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]
    [hoplon.core :as h :refer [defelem when-tpl if-tpl case-tpl for-tpl]]
    [page.layout.sub-menu]))

(defonce
  _first_load_
  (do
    (js/console.debug "First time page load")))

(defn reload []
  (js/console.log "Reload callback was called ")
  (let [bg page.layout.sub-menu/top-nav-bg
        orig-bg @bg]
    (reset! bg "red")
    (h/timeout #(reset! bg orig-bg) 1000)))

(defc color "green")

(def rounded
  {:css (cell= {:color         color
                :border-radius "4px"})})

(defn css-layering-test []
  (vector
    (h/h1 "Reloadable page")
    ((h/button
       :css {:background-color "snow"
             :border           "1px solid purple"
             :color            "blue"}
       :primary true "OK")
      rounded)
    (h/br)
    (h/button "Change color"
              :click #(reset! color "red"))))

(page.layout.sub-menu/html)
