# virtual.list

_Virtual list component for [Prum](https://github.com/roman01la/prum)_

`[org.roman01la/virtual.list "0.1.2"]`

## Usage

See [live demo](https://roman01la.github.io/virtual.list/) & example source code

## API

```clojure
(virtual.list/v-list
  {:rows-count     8
   :max-rows-count 1000
   :row-height     48
   :render-row     render-row
   :overscan-count 4
   :timeout        120})
```
- `:rows-count` — amount of visible rows to render, default is unspecified
- `:max-rows-count` — total amount of rows in a list, default is unspecified
- `:row-height` — height of a single wo in pixels, default is unspecified
- `:render-row` — a function returning a row component, accepts a map of `:idx` & `:scrolling?`, default is unspecified
    - `:idx` — index of current row in `:max-rows-count`
    - `:scrolling?` — whether list is being scrolled or not
- `:overscan-count` — number of rows to render ahead of scrolling direction, default is `0`
- `:timeout` — `scrolling?` timeout is milliseconds, default is `150`
