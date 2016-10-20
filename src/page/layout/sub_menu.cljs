(ns page.layout.sub-menu
  (:require
    [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]
    [hoplon.core :as h :refer [defelem when-tpl if-tpl case-tpl for-tpl]]
    [clojure.string :as str]))

(defn px [v] (str v "px"))

(def css-reset
  "@charset \" UTF-8 \";
  html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6,
  p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del,
  dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup,
  tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label,
  button, legend, table, caption, tbody, tfoot, thead, tr, th, td, article,
  aside, canvas, details, embed, figure, figcaption, footer, header, hgroup,
  menu, nav, output, ruby, section, summary, time, mark, audio, video {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
    background-color: transparent;
  }

  ol, ul { list-style: none; }

  blockquote, q { quotes: none; }

  blockquote:before, blockquote:after,
  q:before, q:after { content: ''; content: none; }

  table { border-collapse: collapse; border-spacing: 0; }

  * { box-sizing: border-box; }

  input[type=\" text \"], input[type=\" email \"],
  input[type=\" password \"], input[type=\" phone \"],
  input[type=\" url \"], input[type=\" search \"],
  input[type=\" submit \"], input[type=\" button \"],
  select, textarea {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;
    background-color: transparent;
  }
")

(def c-text "#262626")
(def c-text-bg "white" #_"#f7f7f7")

(def top-nav-height 48)
(def content-padding 20)
(defc top-nav-bg c-text-bg)

(def striped-background
  ; https://css-tricks.com/stripes-css/
  {:background "repeating-linear-gradient(
                  45deg,
                  transparent, transparent 10px,
                  rgba(255,255,0,0.1) 10px, rgba(255,255,0,0.1) 20px
                )"})

(defelem top-nav-sandbag [attrs kids]
  (h/section
    :css {:position "relative"
          :height   top-nav-height
          ;:border   "1px dotted green"
          }
    attrs kids))

(defelem top-nav [_ kids]
  (h/div :css (cell= {:position         "fixed"
                      :top              0
                      :left             0
                      :z-index          500
                      :width            "100%"
                      :height           top-nav-height
                      :text-align       "center"
                      :background-color top-nav-bg
                      :transition       "background-color 0.5s ease"
                      :box-shadow       (str "rgba(0, 0, 0, 0.15) 0 1px 6px"
                                             ", rgba(0, 0, 0, 0.05) 0 1px 0"
                                             ", inset white 0 -1px 0")})
         kids))

(defelem logo [attrs kids]
  (let [pad 2]
    (h/a :css {:position "absolute"
               :left     0
               :padding  (str (px pad) " 0.5rem 0")}
         :href "#home"
         :click #(do
                  (js/console.debug "ANCHOR" %)
                  true)
         (h/img :src "/acmelogos.com-logo-8.svg"
                :alt "add+"
                :css {:height (px (- top-nav-height (* 2 pad)))}))))

(defn title []
  (h/span :css {:line-height (px top-nav-height)
                :font-size   "1.25rem"
                :font-weight 600}
          "<Screen title>"))

(def menu-width 190)

(defc menu-open? false)

(defelem menu [attrs kids]
  (h/button
    :id "menu-trigger"
    :css {:position    "absolute"
          :right       0
          :top         0
          :width       (px menu-width)
          :height      top-nav-height
          :padding     "10px 10px"
          :text-align  "left"
          :border-left "1px solid rgba(0,0,0,0.2)"
          :cursor      "pointer"
          :user-select "none"
          :outline     "none"}
    :click #(do
             (swap! menu-open? not)
             (.stopPropagation %))
    (h/div :css {:max-width     "140px"
                 :overflow      "hidden"
                 :text-overflow "ellipsis"
                 :white-space   "nowrap"
                 :display       "inline-block"}
           "Investor Irene Kunigunda")
    (h/div :css {:display        "inline"
                 :vertical-align "top"
                 :padding-left   "1em"}
           "▾")))

(defelem dropdown-menu [attrs kids]
  ((h/div
     :id "dropdown-menu"
     :toggle menu-open?
     :css {:position      "fixed"
           :z-index       400
           :right         0
           :top           (px (+ 1 top-nav-height))
           :width         menu-width
           :background    "white"
           :border-radius "0 0 0 6px"
           :box-shadow    "rgba(0, 0, 0, 0.15) 0 0 0 1px,
                           rgba(0, 0, 0, 0.15) 0 1px 20px"})
    attrs kids))

(defelem menu-item [attrs kids]
  (let [hover (cell nil)]
    (h/li :css (cell=
                 {:display          "block"
                  :padding          "0 15px 0 40px"
                  :line-height      (px 32)
                  :text-align       "left"
                  :text-decoration  "none"
                  :cursor           "default"
                  :color            (if hover c-text "inherit")
                  :background-color (if hover "#f7f7f7" "inherit")})
          :mouseenter #(reset! hover true)
          :mouseleave #(reset! hover false)
          attrs kids)))

(defelem sub-menu [attrs kids]
  ((h/div
     :css {:background-color "#e6e6e6"
           :margin           (str/join "px "
                                       [(- content-padding)
                                        (- content-padding)
                                        content-padding nil])
           :box-shadow       "inset 0 -1px 0 0 rgba(0, 0, 0, 0.15)"
           :text-align       "center"
           :height           44})
    attrs kids))

(defelem content [attrs kids]
  ((h/section
     :css {:width      "100%"
           :min-height (str "calc(100% - " top-nav-height "px)")
           :padding    content-padding})
    attrs kids))

(defelem screen []
  (vector
    (h/div
      (apply str (repeat 10 "some very long content. ")))
    (h/section
      :css {:font-size "2em"}
      (for-tpl [i (range 20)]
        (h/div (cell= (str i ". <content>")))))))

(defn html []
  (h/html
    :lang "en"
    :css {:height "100%"
          ;:overflow "hidden"
          }
    (h/head
      (h/title "Submenu layout")
      (h/html-meta :name "viewport"
                   :content "width=device-width, initial-scale=1")
      (h/style css-reset))
    (h/body
      :css {:width    "100%"
            :height   "100%"
            :position "absolute"
            :overflow "auto"}
      :click (fn [event]
               (reset! menu-open? false)
               event)
      (top-nav-sandbag)
      (top-nav
        (logo)
        (title)
        (menu))
      (dropdown-menu
        (h/ul
          (menu-item "Profile")
          (menu-item "Settings"))
        (h/ul :css {:border-top "solid 1px rgba(0, 0, 0, 0.05)"}
              (menu-item "Sign out")))
      (content
        :id "main-content"
        :css striped-background
        (sub-menu :toggle true "<sub nav>")
        (screen)))))
