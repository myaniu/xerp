(function ($) {

    $.inputbox = function (input, options) {

        var $input = $(input); //.attr("autocomplete", "on");
        var $wrap = $(document.createElement("span"));
        var $img = $(document.createElement("img"));
        var $results = $(document.createElement("ul"));
        var $innerWidth = "140";
        options.source = $input.attr("src");
        if (options.source == "") return;
        if ($input.attr("innerWidth")) $innerWidth = $input.attr("innerWidth");
        var wrapClass = $input.attr("class");
        var wrapStyle = $input.attr("style");
        if (wrapClass) $wrap.addClass(wrapClass);
        //if (wrapStyle) $wrap.addStyle(wrapStyle);

        var inputCss = {  
            'height': $input.height() + 'px',
            'width': ($innerWidth - 20) + 'px', //$input.width()
            'padding': "3px 2px 1px 2px"

        };

        $wrap.css({
            'padding': '1px',
            'vertical-align': 'middle',
            'display': 'table-cell'  // do not support ie6?
        });

        $input
                        .removeAttr("src")
            .removeAttr("innerWidth")
                        .removeAttr("class")
                        .removeAttr("style")
                        .css(inputCss)
                        .wrap($wrap);
        $img
                        .attr({ 'src': options.downImg, 'valign': 'absmiddle' })
                        .css({ 'cursor': 'pointer', 'vertical-align': 'middle', 'padding': '1px' })
                        .insertAfter($input);
        //input list
        $results.addClass(options.listClass).css("width", $innerWidth + "px").appendTo('body');

        resetPosition();
        $(window)
                        .load(resetPosition)                // just in case user is changing size of page while loading
                        .resize(resetPosition);

        $input.blur(function () {
            hideItems();
        });

        $input.focus(function () {
            $(this).select();
        });

        $img.click(function () {
            if ($(this).attr("src") == options.downImg)
                inputbox();
            else
                hideItems();
        });

        // help IE users if possible
        try {
            $results.bgiframe();
        } catch (e) { }

        //键盘事件
        if ($.browser.mozilla) {
            $input.keypress(processKey); // onkeypress repeats arrow keys in Mozilla/Opera
            $img.keypress(processKey);
        } else {
            $input.keydown(processKey);         // onkeydown repeats arrow keys in IE/Safari
            $img.keydown(processKey);
        }

        function resetPosition() {
            // requires jquery.dimension plugin
            var offset = $input.offset();
            $results.css({
                top: (offset.top + 2 + $input.height()) + 'px',
                left: (offset.left - 2) + 'px'
            });
        }
        //键盘事件函数
        function processKey(e) {

            // handling up/down/escape requires results to be visible
            // handling enter/tab requires that AND a result to be selected
            if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) ||
                                (/^13$|^9$/.test(e.keyCode) && getCurrentResult()) ||
                                (e.keyCode == 40 && $results.is(':hidden'))) {

                if (e.preventDefault)
                    e.preventDefault();
                if (e.stopPropagation)
                    e.stopPropagation();

                e.cancelBubble = true;
                e.returnValue = false;

                switch (e.keyCode) {
                    case 38: // up
                        prevResult();
                        break;
                    case 40: // down
                        if ($results.is(':visible'))
                            nextResult();
                        else
                            inputbox();
                        break;
                    case 9:  // tab
                    case 13: // return
                        selectCurrentResult();
                        break;
                    case 27: //        escape
                        hideItems();
                        break;
                }
            }
        }

        function hideItems() {
            $results.slideUp('fast');
            $img.attr("src", options.downImg);
        }

        function inputbox() {
            var items = options.source.split(options.delimiter);
            //$input.focus();
            displayItems(items);
        }

        function displayItems(items) {
            if (!items) return;
            if (!items.length) {
                $results.hide();
                return;
            }
            var html = '';
            for (var i = 0; i < items.length; i++)
                html += '<li>' + items[i] + '</li>';
            $results.html(html).slideDown('fast');
            $img.attr("src", options.upImg);
            $results
                                .children('li')
                                .mouseover(function () {
                                    $results.children('li').removeClass(options.selectClass);
                                    $(this).addClass(options.selectClass);
                                })
                                .click(function (e) {
                                    e.preventDefault();
                                    e.stopPropagation();
                                    selectCurrentResult();
                                });
        }

        function getCurrentResult() {
            if (!$results.is(':visible')) return false;
            var $currentResult = $results.children('li.' + options.selectClass);
            if (!$currentResult.length)
                $currentResult = false;
            return $currentResult;
        }

        function selectCurrentResult() {
            $currentResult = getCurrentResult();
            if ($currentResult) {
                $input.val($currentResult.text());
                hideItems();
            }
        }

        function nextResult() {
            $currentResult = getCurrentResult();
            if ($currentResult)
                $currentResult
                                        .removeClass(options.selectClass)
                                        .next()
                                                .addClass(options.selectClass);
            else
                $results.children('li:first-child').addClass(options.selectClass);
        }

        function prevResult() {
            $currentResult = getCurrentResult();
            if ($currentResult)
                $currentResult
                                        .removeClass(options.selectClass)
                                        .prev()
                                                .addClass(options.selectClass);
            else
                $results.children('li:last-child').addClass(options.selectClass);
        }
    }
    $.fn.inputbox = function (source) {
        options = {};
        options.source = source;
        options.delay = options.delay || 100;
        options.listClass = options.listClass || 'inputbox_list';
        options.selectClass = options.selectClass || 'inputbox_over';
        options.delimiter = options.delimiter || ';';
        options.downImg = options.downImg || "/themes/default/images/inputbox/down.gif";
        options.upImg = options.upImg || "/themes/default/images/inputbox/up.gif";
        this.each(function () {
            new $.inputbox(this, options);
        });
        return this;
    };
})(jQuery);
