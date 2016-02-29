var LiveSearch = {
    init: function (input, conf) {
        var config = {
            url: conf.url || false,
            appendTo: conf.appendTo || 'after',
            data: conf.data || {},
            content: []
        };

        var appendTo = appendTo || 'after';

        input.setAttribute('autocomplete', 'off');

        // Create search container
        var searchPlaceHolder = document.createElement('div');

        searchPlaceHolder.id = 'live-search-' + input.name;

        searchPlaceHolder.classList.add('live-search');

        // Append search container
        if (appendTo == 'after') {
            input.parentNode.classList.add('live-search-wrap');
            input.parentNode.insertBefore(searchPlaceHolder, input.nextSibling);
        }
        else {

            appendTo.appendChild(searchPlaceHolder);
        }
        var inputWidth = $(input).width();
        $(searchPlaceHolder).width(inputWidth).invisible().addClass("col-sm-10");

        // Hook up keyup on input
        input.addEventListener('keyup', function (e) {
            if (this.value != this.liveSearchLastValue) {
                this.classList.add('loading');

                var q = this.value;

                // Clear previous ajax request
                if (this.liveSearchTimer) {
                    clearTimeout(this.liveSearchTimer);
                }

                // Build the URL
                var url = config.url + q;

                if (config.data) {
                    if (url.indexOf('&') != -1 || url.indexOf('?') != -1) {
                        url += '&' + LiveSearch.serialize(config.data);
                    }
                    else {
                        url += '?' + LiveSearch.serialize(config.data);
                    }
                }

                // Wait a little then send the request
                var self = this;

                /*$(document).keyup(function(e) {
                    if (e.keyCode == 27) $('.cancel').click(function () {
                        $("#" + searchPlaceHolder.id).empty();
                        $("#" + searchPlaceHolder.id).invisible();
                    });   // esc
                });*/

                this.liveSearchTimer = setTimeout(function () {
                    if (q) {
                        $.ajax({
                            type: 'GET',
                            url: url,
                            success: function (data) {
                                self.classList.remove('loading');
                                $("#" + searchPlaceHolder.id).empty();

                                if ($.isArray(data)) {

                                    if (data.length > 0) {
                                        $(searchPlaceHolder).visible();
                                    }
                                    else {
                                        $(searchPlaceHolder).invisible();
                                    }
                                    var ulElem = $('<ul>');
                                    $('#' + searchPlaceHolder.id).append(ulElem);
                                    data.forEach(function (eachData) {

                                        config.content.push(eachData);
                                        ulElem.width($(searchPlaceHolder).width())
                                            .addClass("live-search-wrap div.live-search ul")
                                            .append($('<li>')
                                                .addClass("live-search-wrap div.live-search ul")
                                                .css('background-color', '#ffffff')
                                                .text(eachData)
                                                .attr('value', eachData)
                                                .hover(function () {
                                                    $(this).css('background-color', '#87CEFA');
                                                })
                                                .mouseout(function() {
                                                    $(this).css('background-color', '#ffffff');
                                                })
                                                .click(function () {
                                                    var str = $(this).text();
                                                    $(input).val(str);
                                                    $("#" + searchPlaceHolder.id).empty();
                                                    $(searchPlaceHolder).invisible();
                                                })
                                        );
                                    })
                                }
                                //container.innerHTML = data;
                            }
                        });
                    }
                    else {
                        searchPlaceHolder.innerHTML = '';
                        $(searchPlaceHolder).invisible();
                    }
                }, 300);

                this.liveSearchLastValue = this.value;
            }
        });
    },

    // http://stackoverflow.com/questions/1714786/querystring-encoding-of-a-javascript-object
    serialize: function (obj) {
        var str = [];

        for(var p in obj) {
            if (obj.hasOwnProperty(p)) {
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
            }
        }

        return str.join('&');
    }
};

jQuery.fn.visible = function() {
    this.css('visibility', 'visible');
    return this.css('display', 'block');
};

jQuery.fn.invisible = function() {
    this.css('visibility', 'hidden');
    return this.css('display', 'none');
};


if (typeof(jQuery) != 'undefined') {
    jQuery.fn.liveSearch = function (conf) {
        return this.each(function () {
            LiveSearch.init(this, conf);
        });
    };
}