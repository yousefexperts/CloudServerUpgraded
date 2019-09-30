(function(f){if(typeof exports==="object"&&typeof module!=="undefined"){module.exports=f()}else if(typeof define==="function"&&define.amd){define([],f)}else{var g;if(typeof window!=="undefined"){g=window}else if(typeof global!=="undefined"){g=global}else if(typeof self!=="undefined"){g=self}else{g=this}g.countdown = f()}})(function(){var define,module,exports;return (function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

/**
 * Countdown constructor
 * @param {HTMLElement} el DOM node of the countdown
 * @param {int} cd
 * @param {Object} options (optional) Options for the plugin
 */
var Countdown = function (el, cd, options) {

    this.el = el;
    this.cd = cd;
    this.options = {
        state: {
            text: '',
            color: 'black',
            'font-size': '12px',
            scale: 0.8
        },
        maxNumWidth: 26, // max num width
        placeholderColor: '#f1f1f1', // num placeholder color
        color: '#b00d1d', // num color
        borderWidth: 4, // border width
        colonWidth: 5, // ':' width
        dotWidth: 3, // '.' width
        numMargin: 3, // num margin
        microMargin: 1, // little '.' & ':' margin
        bm: [1, 2, 2, 1],
        onEnd: function () {
            return;
        },
        onRunning: [{left:0, callback: function(){return;}}]
    }
    this.timer = null;

    // Dom container
    this.container = null;
    // digit reference array
    this.refs = null;
    // digit class
    this.digitClasses = ['zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'];

    /**
     * 插件初始化，包括CSS以及数字
     */
    this.init = function () {
        this.cd = eval('(' + this.cd + ')');
        this.container = this.initCss();
        this.refs = this.initNums();
    }.bind(this);

    /**
     * 初始化Css
     */
    this.initCss = function () {
        var id = this.randID(4);//'countdown-dynamic';
        var html = '<div style="width:100%;height:100%;position:relative;"><div style="position:absolute;left:0;top:0;right:0;bottom: ' + (this.options['state'] && this.options['state']['text'] ? parseInt(this.options['state']['font-size']) + 2 + "px" : '0') + ';" id="' + id + '" class="' + id + '"></div>';
        if (this.options['state'] && this.options['state']['text']) {
            html += '<p style="-webkit-margin-before: 0em; -webkit-margin-after: 0em;width:100%;position:absolute;bottom:0px;line-height:' + (parseInt(this.options['state']['font-size']) + 2) + 'px;font-size:' + this.options['state']['font-size'] + ';transform: scale('+this.options['state']['scale']+');-webkit-transform: scale('+this.options['state']['scale']+');-moz-transform: scale('+this.options['state']['scale']+');-ms-transform: scale('+this.options['state']['scale']+'); -o-transform: scale('+this.options['state']['scale']+');text-align: center;color:' + this.options['state']['color'] + ';">' + this.options['state']['text'] + '</p>';
        }
        html += '</div>';
        this.el.innerHTML = html;

        var container = this.el.querySelector('#' + id);
        var containerWidth = Math.floor(container.clientWidth);
        var containerHeight = Math.floor(container.clientHeight);

        var colonWidth = this.options['colonWidth'];
        var dotWidth = this.options['dotWidth'];
        var margin = this.options['numMargin'];
        var microMargin = this.options['microMargin'];

        // 数字border 和 宽度
        var borderW = this.options['borderWidth'];
        var halfBorderW = Math.floor(borderW / 2);

        // 间距
        var bm = this.options['bm'];
        var bmSum = 0;
        for (var i = 0; i < bm.length; i++) {
            bmSum += bm[i];
        }

        var days = parseInt(this.cd / (24 * 3600));
        var dayCount = days == 0 ? 0 : ((days + '').length + 1); // add '天'
        // XXX天XX:XX:XX.x
        var allNumWidth = Math.floor(containerWidth - (colonWidth + margin * 2) * 2 - (dayCount + 6) * (margin * 2) - (dotWidth + microMargin * 2) - microMargin * 2);
        var numWidth = Math.min(allNumWidth / (dayCount + 6.5), this.options['maxNumWidth']);
        var numHeight = Math.min(containerHeight, numWidth * 2 + bmSum);
        numWidth = Math.floor(Math.min((numHeight - bmSum) / 2, numWidth));
        var numLen = Math.max(numWidth - borderW * 2, 0);
        numHeight = borderW + numLen + bm[0] + bm[1] + borderW + bm[2] + numLen + bm[3] + borderW;

        var offset = Math.floor(containerWidth - ((numWidth + margin * 2) * (dayCount + 6) + ((colonWidth + margin * 2) * 2) + (dotWidth + microMargin * 2) + (numWidth / 2 + microMargin * 2)));
        offset = Math.max(offset - 1, 0);
        offset = Math.floor(offset / 2);

        // 容器padding
        var paddingLeft = offset;
        var paddingTop = (containerHeight - numHeight) / 2;

        var numSetting = [];
        numSetting[0] = {
            'w': numLen,
            'h': borderW,
            'left': borderW,
            'top': 0,
            'border_before': 'border-width:0 ' + borderW + 'px ' + borderW + 'px 0;border-right-color:inherit;left:-' + borderW + 'px;',
            'border_after': 'border-width:0 0 ' + borderW + 'px ' + borderW + 'px; border-left-color:inherit;right:-' + borderW + 'px;'
        };
        numSetting[1] = {
            'w': numLen,
            'h': borderW,
            'left': borderW,
            'top': borderW + numLen + bm[0] + bm[1],
            'border_before': 'border-width:' + (borderW - 1) / 2 + 'px ' + (borderW - 1) + 'px ' + (halfBorderW + 1) + 'px' + ';border-right-color:inherit;left:-' + ((borderW - 1) * 2) + 'px;',
            'border_after': 'border-width:' + (borderW - 1) / 2 + 'px ' + (borderW - 1) + 'px ' + (halfBorderW + 1) + 'px' + '; border-left-color:inherit;right:-' + ((borderW - 1) * 2) + 'px;'
        };
        numSetting[2] = {
            'w': numLen,
            'h': borderW,
            'left': borderW,
            'top': borderW + numLen + bm[0] + bm[1] + borderW + bm[2] + numLen + bm[3],
            'border_before': 'border-width:' + borderW + 'px ' + borderW + 'px 0 0;border-right-color:inherit;left:-' + borderW + 'px;',
            'border_after': 'border-width:' + borderW + 'px 0 0 ' + borderW + 'px; border-left-color:inherit;right:-' + borderW + 'px;'
        };
        numSetting[3] = {
            'w': borderW,
            'h': numLen,
            'left': 0,
            'top': borderW + bm[0],
            'border_before': 'border-width:0 ' + borderW + 'px ' + borderW + 'px 0;border-bottom-color:inherit;top:-' + borderW + 'px;',
            'border_after': 'border-width:0 0 ' + borderW + 'px ' + borderW + 'px; border-left-color:inherit;bottom:-' + borderW + 'px;'
        };
        numSetting[4] = {
            'w': borderW,
            'h': numLen,
            'left': borderW + numLen,
            'top': numSetting[3]['top'],
            'border_before': 'border-width:0 0 ' + borderW + 'px ' + borderW + 'px;border-bottom-color:inherit;top:-' + borderW + 'px;',
            'border_after': 'border-width:' + borderW + 'px 0 0 ' + borderW + 'px; border-top-color:inherit;bottom:-' + borderW + 'px;'
        };
        numSetting[5] = {
            'w': borderW,
            'h': numLen,
            'left': 0,
            'top': bm[0] + borderW + numLen + borderW + bm[1] + bm[2],
            'border_before': 'border-width:0 ' + borderW + 'px ' + borderW + 'px 0;border-bottom-color:inherit;top:-' + borderW + 'px;',
            'border_after': 'border-width:0 0 ' + borderW + 'px ' + borderW + 'px; border-left-color:inherit;bottom:-' + borderW + 'px;'
        };
        numSetting[6] = {
            'w': borderW,
            'h': numLen,
            'left': borderW + numLen,
            'top': numSetting[5]['top'],
            'border_before': 'border-width:0 0 ' + borderW + 'px ' + borderW + 'px;border-bottom-color:inherit;top:-' + borderW + 'px;',
            'border_after': 'border-width:' + borderW + 'px 0 0 ' + borderW + 'px; border-top-color:inherit;bottom:-' + borderW + 'px;'
        };

        var style = "#" + id + "{padding-left:" + paddingLeft + "px; padding-right:" + paddingLeft + "px; padding-top:" + paddingTop + "px;overflow:hidden;}\
                     #" + id + " p{-webkit-margin-before: 0em;-webkit-margin-after: 0em;}\
                     #" + id + " .count-down-digit{text-align:left;position: relative;width: " + numWidth + "px;height: " + numHeight + "px;display: inline-block;margin: 0 " + margin + "px;} \
                     #" + id + " .count-down-digit.half{width:" + (Math.floor(numWidth / 2)) + "px;margin:0 " + microMargin + "px;} \
                     #" + id + " .count-down-digit.half .half{width:100%;height:100%;transform:scale(0.5, 0.5) translate(0, -150%);-webkit-transform:scale(0.5, 0.5) translate(0, -150%); -moz-transform:scale(0.5, 0.5) translate(0, -150%); -ms-transform:scale(0.5, 0.5) translate(0, -150%); -o-transform:scale(0.5, 0.5) translate(0, -150%);position:absolute;top:100%;} \
                     #" + id + " .count-down-digit span{position: absolute} \
                     #" + id + " .count-down-digit span:before,#" + id + " .count-down-digit span:after{content: '';position: absolute;width: 0; height: 0;border: " + borderW + "px solid transparent;} ";

        for (var i = 0; i < 7; i++) {
            style += "#" + id + " .count-down-digit .d" + (i + 1) + "{height:" + numSetting[i].h + "px;width:" + numSetting[i].w + "px;left:" + numSetting[i].left + "px;top:" + numSetting[i].top + "px;background-color: " + this.options['placeholderColor'] + ";border-color: " + this.options['placeholderColor'] + ";} ";
            style += "#" + id + " .count-down-digit .d" + (i + 1) + ":before{" + numSetting[i]['border_before'] + "} ";
            style += "#" + id + " .count-down-digit .d" + (i + 1) + ":after{" + numSetting[i]['border_after'] + "} ";
        }
        var colors = {
            'zero': [1, 5, 7, 3, 6, 4],
            'one': [5, 7],
            'two': [1, 5, 2, 6, 3],
            'three': [1, 5, 2, 7, 3],
            'four': [4, 2, 5, 7],
            'five': [1, 4, 2, 7, 3],
            'six': [1, 4, 6, 3, 7, 2],
            'seven': [1, 5, 7],
            'eight': [1, 2, 3, 4, 5, 6, 7],
            'nine': [1, 4, 2, 5, 7]
        };

        var inArray = function (e, arr) {
            for (var k = 0; k < arr.length; k++) {
                if (arr[k] == e) {
                    return k;
                }
            }
            return -1;
        };

        for (i in colors) {
            var colorIndexs = colors[i];
            for (var j = 0; j < 7; j++) {
                if (inArray(j + 1, colorIndexs) >= 0) {
                    style += "#" + id + " .count-down-digit." + i + " .d" + (j + 1) + "{background-color:" + this.options['color'] + "; border-color:" + this.options['color'] + ";} ";
                } else {
                    style += "#" + id + " .count-down-digit." + i + " .d" + (j + 1) + "{background-color:" + this.options['placeholderColor'] + "; border-color:" + this.options['placeholderColor'] + ";} ";
                }
            }
        }

        style += "#" + id + " .count-down-digit .text{color:" + this.options['placeholderColor'] + "font-size:" + numWidth + "px;position:absolute;top:100%;transform:translate(0, -100%);} ";
        style += "#" + id + " .count-down-digit.enable .text{color:" + this.options['color'] + "} ";

        // 分号
        style += "#" + id + " .count-down-digit.colon{width: " + colonWidth + "px;}";
        style += "#" + id + " .count-down-digit .dots{width:100%; height:100%;} ";
        style += "#" + id + " .count-down-digit .dots:before{background:" + this.options['placeholderColor'] + ";width:" + colonWidth + "px; height: " + colonWidth + "px;content: '';position: absolute;left: 0;top: " + (numHeight / 3 - colonWidth / 2) + "px;} ";
        style += "#" + id + " .count-down-digit .dots:after{background:" + this.options['placeholderColor'] + ";width:" + colonWidth + "px; height: " + colonWidth + "px;content: '';position: absolute;left: 0;top: " + (numHeight / 3 * 2 - colonWidth / 2) + "px;}";
        style += "#" + id + " .count-down-digit.enable .dots:before{background:" + this.options['color'] + " !important;}";
        style += "#" + id + " .count-down-digit.enable .dots:after{background:" + this.options['color'] + " !important;}";

        // 小点
        style += "#" + id + " .count-down-digit.small{width: " + dotWidth + "px;margin:0 " + microMargin + "px}";
        style += "#" + id + " .count-down-digit .small-dots{width:100%; height:100%;} ";
        style += "#" + id + " .count-down-digit .small-dots:after{background:" + this.options['placeholderColor'] + ";width:" + dotWidth + "px; height: " + dotWidth + "px;content: '';position: absolute;left: 0;top: " + (numHeight - dotWidth) + "px;}";
        style += "#" + id + " .count-down-digit.enable .small-dots:after{background:" + this.options['color'] + " !important;}";

        var node = document.createElement('style');
        node.innerHTML = style;
        document.querySelector('head').appendChild(node)
        return container;
    }.bind(this);

    /**
     * 初始化数字
     */
    this.initNums = function () {
        var days = parseInt(this.cd / (24 * 3600));
        var hours = parseInt((this.cd % 86400) / 3600);
        var mins = parseInt(((this.cd % 86400) % 3600) / 60);
        var secs = parseInt(((this.cd % 86400) % 3600) % 60);

        var refs = {
            daysRef: [],
            hoursRef: [],
            hourColonRef: null,
            minsRef: [],
            minColonRef: null,
            secondsRef: [],
            dotRef: null,
            microSecondsRef: null
        };

        // 几天
        refs.daysRef = this.drawDays(days);
        // 小时
        refs.hoursRef.push(this.drawNum(parseInt(hours / 10)));
        refs.hoursRef.push(this.drawNum(hours % 10));
        refs.hourColonRef = this.drawColon();
        // 分
        refs.minsRef.push(this.drawNum(parseInt(mins / 10)));
        refs.minsRef.push(this.drawNum(mins % 10));
        refs.minColonRef = this.drawColon();
        // 秒
        refs.secondsRef.push(this.drawNum(parseInt(secs / 10)));
        refs.secondsRef.push(this.drawNum(secs % 10));
        refs.dotRef = this.drawDot();
        // 毫秒
        refs.microSecondsRef = this.drawNum(0, true); // 每100毫秒更新一次
        return refs;
    }.bind(this);

    /**
     * 启动Countdown
     */
    this.start = function () {
        this.stop();

        var refs = this.refs;
        var that = this;
        var countDown = this.cd;

        refs.daysRef && refs.daysRef.length > 0 && this.addClass(refs.daysRef[refs.daysRef.length - 1], 'enable');
        refs.hourColonRef && this.addClass(refs.hourColonRef, 'enable');
        refs.minColonRef && this.addClass(refs.minColonRef, 'enable');
        refs.dotRef && this.addClass(refs.dotRef, 'enable');

        var index = 0;
        var timer = setInterval(function () {
            refs.microSecondsRef && that.removeClass(refs.microSecondsRef, that.digitClasses[(index + 1) % 10]) && that.addClass(refs.microSecondsRef, that.digitClasses[index]);
            index = (index + 9) % 10;
            if (index == 9) {
                countDown--;
                countDown = Math.max(countDown, 0);
                // 天
                var days = parseInt(countDown / 86400);
                that.setDaysNum(refs.daysRef, days);

                // 时
                var hours = parseInt((countDown % 86400) / 3600);
                that.setNum(refs.hoursRef, hours);

                // 分
                var mins = parseInt(((countDown % 86400) % 3600) / 60);
                that.setNum(refs.minsRef, mins);

                // 秒
                var secs = parseInt(((countDown % 86400) % 3600) % 60);
                that.setNum(refs.secondsRef, secs);

                if (that.options['onRunning'] && Object.prototype.toString.call(that.options['onRunning']) === '[object Array]') {
                    for (var ele = 0; ele < that.options['onRunning'].length; ele++) {
                        if (that.options['onRunning'][ele]['left'] == countDown && typeof that.options['onRunning'][ele]['callback'] == 'function') {
                            that.options['onRunning'][ele]['callback'](countDown);
                        }
                    }
                }

                if (countDown <= 0) {
                    clearInterval(timer);
                    that.options['onEnd'] && that.options['onEnd']();
                }
            }
        }, 100);
        return timer;
    }.bind(this);

    this.setDaysNum = function (refsArr, days) {
        if (!refsArr) {
            return;
        }
        var len = Math.max(refsArr.length - 1, 0);
        this.setNum(refsArr, days, len);
    };

    this.setNum = function (refsArr, num, len) {
        if (!refsArr) {
            return;
        }
        len = len || 2;
        for (var i = 0; i < len; i++) {
            var c = this.digitClasses[num % 10];
            var preClass = refsArr[len - 1 - i].getAttribute('preClass');
            preClass != c && this.removeClass(refsArr[len - 1 - i], preClass) && this.addClass(refsArr[len - 1 - i], c) && refsArr[len - 1 - i].setAttribute('preClass', c);
            num = parseInt(num / 10);
        }
    }

    // add Dom node class
    this.addClass = function (el, className) {
        if (!el) {
            return false;
        }
        if (el.classList) {
            el.classList.add(className);
        } else if (!this.hasClass(el, className)) {
            el.className += " " + className;
        }
        return this;
    }.bind(this);

    // check Dom Node whether has class
    this.hasClass = function (el, className) {
        if (!el) {
            return false;
        }
        if (el.classList) {
            return el.classList.contains(className);
        } else {
            return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'));
        }
    }.bind(this);

    // remove Dom node class
    this.removeClass = function (el, className) {
        if (!el) {
            return false;
        }
        if (el.classList) {
            el.classList.remove(className);
        } else if (hasClass(el, className)) {
            var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
            el.className = el.className.replace(reg, ' ')
        }
        return this;
    }.bind(this);

    this.stop = function () {
        if (this.timer) {
            clearInterval(this.timer);
        }
    }.bind(this);

    this.restart = function (cd, options) {
        this.stop();
        this.cd = cd;
        this.options = this.extend(this.options, options);
        this.init();
        this.start();
    }.bind(this);

    this.drawDays = function (days) {
        if (days > 0) {
            days += '';
            var nodes = [];
            for (var i = 0; i < days.length; i++) {
                nodes.push(this.drawNum());
            }
            var textNodes = this.drawText('天');
            for (var i = 0; i < textNodes.length; i++) {
                nodes.push(textNodes[i]);
            }
            return nodes;
        } else {
            return null;
        }
    }.bind(this);

    this.drawNum = function (digit, half) {
        digit = digit || 0;
        half = half || false;
        var css = half ? 'half' : '';
        var html = '<div class="count-down-digit ' + this.digitClasses[digit % 10] + ' ' + css + '" preClass="' + this.digitClasses[digit % 10] + '">';
        if (half) {
            html += '<div class="half">';
        }
        for (var i = 0; i < 7; i++) {
            html += '<span class="d' + (i + 1) + '"></span>';
        }
        if (half) {
            html += '</div>';
        }
        html += '</div>';
        var node = document.createElement('div');
        node.innerHTML = html;
        node = node.firstChild;
        this.container.appendChild(node);
        return node;
    }.bind(this);

    this.drawText = function (text) {
        var nodes = [];
        for (var i = 0; i < text.length; i++) {
            nodes.push(this.drawWord(text.substr(i, 1)));
        }
        return nodes;
    }.bind(this);

    this.drawWord = function (word) {
        var html = '<div class="count-down-digit enable">';
        html += '<span class="text">' + word + '</span>';
        html += '</div>';
        var node = document.createElement('div');
        node.innerHTML = html;
        node = node.firstChild;
        this.container.appendChild(node);
        return node;
    }.bind(this);

    this.drawColon = function () {
        var html = '<div class="count-down-digit enable colon">';
        html += '<div class="dots"></div>';
        html += '</div>';
        var node = document.createElement('div');
        node.innerHTML = html;
        this.container.appendChild(node.firstChild);
        return node.firstChild;
    }.bind(this);

    this.drawDot = function () {
        var html = '<div class="count-down-digit enable small">';
        html += '<div class="small-dots"></div>';
        html += '</div>';
        var node = document.createElement('div');
        node.innerHTML = html;
        node = node.firstChild;
        this.container.appendChild(node);
        return node;
    }.bind(this);

    this.extend = function () {
        for (var i = 1; i < arguments.length; i++)
            for (var key in arguments[i])
                if (arguments[i].hasOwnProperty(key))
                    arguments[0][key] = arguments[i][key];
        return arguments[0];
    }.bind(this);

    this.randID = function (len) {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (var i = 0; i < len; i++) {
            text += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        return text;
    }.bind(this);

    // set up
    this.options = this.extend(this.options, options);
    // initialize
    this.init();
    // start countdown
    this.start();
}

module.exports = Countdown;
},{}]},{},[1])(1)
});