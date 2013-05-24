/*
*  @author Junyu
*  @description 基于jquery的扩展库
**
用途：
对待需要弹出层来显多多控件时使用。(现在对其之前的版本1.1的功能进行精简.日后如果用到再加上吧)

格式：

    //触发者
    <input id="tinput" type="text"  name="it" value="inputOne " />

    //显示层
    <div class="listbox" rel="#tinput">
    </div>
    *注意 rel这个属性 目的是找到相对于此层的关联着（触发者） ，这里面的字符串意义是 一个 JQuery 的选择器字符串。  不一定费用#来通过Id寻找。
             也可以通过位置来查找触发者。这样可能更好些.

使用：
    //页面初始化加载时，或放入公共初始化库
    $("div.listbox").listbox();//单个元素和多元素都可以

*/

(function ($) {
    $.extend($.fn, {
        listbox: function (options) {
            this.version = "1.0";
            this.box = this;
            this.rel = null;

            this.Init = function () {
                var self = this;
                var box = this.box;
                var rel = this.rel = $(self.box.attr("rel"));
                if (rel.length < 1) {
                    box.css("display", "none");
                    return self;
                }
                //css
                box
                .css("display", "none")
                .css("position", "absolute")
                .css("color", "#3F3F3F")
                .css("border-style", "solid")
                .css("border-width", "1px")
                .css("border-color", "#989898")
                .css("background-color", "white");
                var position = getBoxPosition(rel, box);
                box
                .css("left", position.left)
                .css("top", position.top)
                .css("min-width", position.width);
                //events
                rel.bind("click", { self: self }, eventShow);

                return self;
            };
            //重置位置
            this.ResetPosition = function () {
                var position = getBoxPosition(this.rel, this.box);
                this.box
                   .css("left", position.left)
                   .css("top", position.top);
                return this;
            }
            //显示
            this.Show = function () {
                var self = this;
                this.ResetPosition();
                $(document).bind("mousedown", { self: self }, eventHide);
                $(this.box).slideDown(150);
                return self;
            };
            //隐藏
            this.Hide = function () {
                $(document).unbind("mousedown", eventHide);
                $(this.box).slideUp(50);
                return this;
            };

            //获得位置
            function getBoxPosition(rel, box) {
                var wh = {
                    "width": $(rel).width()
                    , "height": $(rel).height()
                    , "padding-top": $(rel).css("padding-top")
                    , "padding-bottom": $(rel).css("padding-bottom")
                    , "padding-left": $(rel).css("padding-left")
                    , "padding-right": $(rel).css("padding-right")
                    , "border-top-width": $(rel).css("border-top-width")
                    , "border-bottom-width": $(rel).css("border-bottom-width")
                    , "border-left-width": $(rel).css("border-left-width")
                    , "border-right-width": $(rel).css("border-right-width")
                    , "box_border-left-width": $(box).css("border-left-width")
                    , "box_border-right-width": $(box).css("border-right-width")
                    , "box_padding-left": $(box).css("padding-left")
                    , "box_padding-right": $(box).css("padding-right")
                    , "body_margin-left": $(document.body).css("margin-left")
                    , "body_margin-top": $(document.body).css("margin-top")
                };
                $.each(wh, function (k, v) {
                    v = v.toString().replace("px", "").replace("auto", "").replace("inherit", "").replace("medium", "").replace("thick", "").replace("thin", "");
                    if (v) v = Number(v);
                    else v = 0;
                    wh[k] = v;
                });
                var position = {
                    height: (wh["height"] + wh["padding-top"] + wh["padding-bottom"] + wh["border-top-width"] + wh["border-bottom-width"])
                    ,
                    width: (wh["width"] + wh["padding-left"] + wh["padding-right"] + wh["border-left-width"] + wh["border-right-width"] - wh["box_border-left-width"] - wh["box_border-right-width"] - wh["box_padding-left"] - wh["box_padding-right"])
                    ,
                    top: 0
                    ,
                    left: 0
                };
                var odisplay = box.css("display");
                var relOffset = rel.offset();
                box.css("display", "block");
                var pboxOffset = box.offsetParent().offset();
                box.css("display", odisplay);
                position.top = (relOffset.top - pboxOffset.top) + position.height + 1;
                position.left = (relOffset.left - pboxOffset.left);
                if ($(document.body).css("position") != "relative") {
                    position.top += wh["body_margin-top"];
                    position.left += wh["body_margin-left"];
                }
                return position;
            };
            function eventShow(event) {
                event.stopPropagation();
                event.data.self.Show();
            };
            function eventHide(event) {
                if (
                    ($(event.data.self.box)[0] != $(event.srcElement)[0])
                    &&
                    $(event.data.self.box).has($(event.srcElement)[0]).length < 1) {
                    event.data.self.Hide();
                }
            };

            //
            if (this.length == 1) { return this.Init(); }
            return this.each(function () {
                $(this).listbox(options);
            });

        }
    });
})(jQuery);
