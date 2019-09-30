var Countdown = require('./countdown');
var DATA = 'countdown';
/**
 * jQuery countdown plugin
 * @param options (optional) options
 * @param cd countdown
 */
jQuery.fn.countdown = function (options, cd) {
    return $.each(this, function (i, el) {
                var _el = $(el);
                new Countdown(el, _el.attr(DATA) || cd, options)
            });
}
module.exports = Countdown;