(ns 한글코딩.뷰
  (:use [미생.기본]
        [hiccup.core]
        [hiccup.page])
  (:require [clojure.string :as s]
            [ring.util.response :as 응답]))

(함수- 머리말 []
  (가정 [링크 (fn [주제]
                (str "/" (s/replace 주제 #"\s" "-")))]
    [:nav.머리말
     [:section.container
      [:a.제목 {:href "/"} [:h1.제목 "한글코딩"]]
      [:ul.머리말-목록.float-right
       (for [주제 ["유니코드"
                   "프로그래밍 언어"
                   "도구 및 설정"
                   "소문"]]
         [:li.머리말-목록-링크 [:a {:href (링크 주제)} 주제]])]]]))

(함수- 꼬리말 []
  (가정 [링크 (fn [주소 텍스트]
                [:li.꼬리말-목록-링크 [:a {:href 주소} 텍스트]])]
    [:footer.꼬리말
     [:section.container
      [:ul.꼬리말-목록
       (링크 "/고마운분들" "고마운 분들")
       (링크 "/돕는방법" "돕는 방법")]
      [:ul.꼬리말-목록.float-right
       (링크 "/작성자" "2016년 김대현 올림")]]]))

(함수- GA [코드]
  [:script
   "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', '" 코드 "', 'auto');
  ga('send', 'pageview');"])

(함수 레이아웃 [속성 & 본문]
  (html5
   [:html {:lang "ko"}
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:content "IE=edge" :http-equiv "X-UA-Compatible"}]
     [:meta {:content "한글로 코딩합시다" :name "description"}]
     [:meta {:content "https://keybase.io/hatemogi" :name "author"}]
     [:meta {:content "width=device-width, initial-scale=1.0, minimum-scale=1.0"
       :name "viewport"}]
     [:title "한글코딩" (만약-가정 [제목 (:제목 속성)] (str " | " 제목))]
     (map include-css ["/css/normalize.css"
                       "/css/milligram.min.css"
                       "/css/codemirror.css"
                       "/css/색/github.css"
                       "/css/theme/neat.css"
                       "/css/theme/neo.css"
                       "/css/theme/ttcn.css"
                       "/css/한글코딩.css"])]
    [:body
     [:div.포장
      (머리말)
      (into [:main.container] 본문)
      (꼬리말)]
     (map include-js ["/js/jquery-2.2.2.min.js"
                      "/js/highlight.pack.js"
                      "/js/marked.min.js"
                      "/js/codemirror.js"
                      "/js/mode/clojure/clojure.js"
                      "/js/mode/javascript/javascript.js"
                      "/js/mode/clike/clike.js"
                      "/js/mode/sql/sql.js"
                      "/js/한글코딩.js"])
     #_(GA "UA-75606874-1")]
    ]
   ))

(함수 마크다운 [파일명]
  [:div.마크다운 {:data-markdown 파일명}])

(함수 하이라이트 [파일명]
  [:pre.소스 [:code {:data-src 파일명}]])

(함수 첫페이지 [요청]
  (레이아웃 {:제목 "페이지 제목"}
            [:section (마크다운 "/md/소개.md")]
            [:section (마크다운 "/md/공감.md")]
            [:h2 "언어별 예제"]
            [:h3 "SQL 예제"]
            (하이라이트 "/sql/프로젝트.sql")))

(함수 작성자소개 [요청]
  (레이아웃 {:제목 "작성자 소개"}
            [:section.container
             [:div.row
              [:div.column.column-25 [:img.프로필 {:src "/img/프로필.jpg"}]]
              [:div.colunm.column-75 [:div (마크다운 "/md/작성자.md")]]]]))

(함수 찾을수없어요 [요청]
  (레이아웃 {}
            [:h1 "찾을 수 없습니다"]
            [:section
             [:a.button {:href "/"} "첫페이지로 가기"]]))
