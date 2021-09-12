$(document).ready( function () {
    $("#formToValidate").validate( {
        rules: {
            login: {
                required: true,
                pattern: /^[a-zA-Z][a-zA-Z0-9-_.]{2,32}$/
            },
            firstName: {
                required: true,
                pattern: /^[A-Z\u0400-\u04ff][a-z\u0400-\u04ff.'-]{2,35}$/
            },
            lastName: {
                required: true,
                pattern: /^[A-Z\u0400-\u04ff][a-z\u0400-\u04ff.'-]{2,35}$/
            },
            email: {
                required: true,
                email: true
            },
            city: {
                required: true,
                pattern: /^[a-zA-Z\u0400-\u04ff]+(?:[\s-][a-zA-Z\u0400-\u04ff]+)*$/
            },
            password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
            },
            confirm: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/,
                equalTo: "#password"
            },
            postal: {
                required: true,
                pattern: /^[0-9]{6}$/
            },
            address: {
                required: true,
                pattern: /^[a-zA-Z\u0400-\u04ff]+(?:[\s-.,]+[a-zA-Z\u0400-\u04ff0-9]+)*$/
            },
        },
        messages: {
            firstName: {
                pattern: "\u0418\u043C\u044F \u0434\u043E\u043B\u0436\u043D\u043E \u043D\u0430\u0447\u0438\u043D\u0430\u0442\u044C\u0441\u044F \u0441 \u0431\u043E\u043B\u044C\u0448\u043E\u0439 \u0431\u0443\u043A\u0432\u044B \u0438 \u0431\u044B\u0442\u044C \u0434\u043B\u0438\u043D\u043D\u0435\u0435 2 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432"
            },
            lastName: {
                pattern: "\u0424\u0430\u043C\u0438\u043B\u0438\u044F \u0434\u043E\u043B\u0436\u043D\u0430 \u043D\u0430\u0447\u0438\u043D\u0430\u0442\u044C\u0441\u044F \u0441 \u0431\u043E\u043B\u044C\u0448\u043E\u0439 \u0431\u0443\u043A\u0432\u044B \u0438 \u0431\u044B\u0442\u044C \u0434\u043B\u0438\u043D\u043D\u0435\u0435 2 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432"
            },
            login: {
                pattern: "\u041B\u043E\u0433\u0438\u043D \u0434\u043E\u043B\u0436\u0435\u043D \u0431\u044B\u0442\u044C \u0434\u043B\u0438\u043D\u043D\u0435\u0435 2\u0443\u0445 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432 \u0438 \u0432\u043A\u043B\u044E\u0447\u0430\u0442\u044C \u0432 \u0441\u0435\u0431\u044F \u0442\u043E\u043B\u044C\u043A\u043E \u043B\u0430\u0442\u0438\u043D\u0441\u043A\u0438\u0435 \u0431\u0443\u043A\u0432\u044B, \u0446\u0438\u0444\u0440\u044B \u0438 \u0441\u0438\u043C\u0432\u043E\u043B\u044B - _ ."
            },
            password: {
                pattern: "\u0412\u0430\u0448 \u043F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0432\u043A\u043B\u044E\u0447\u0430\u0442\u044C \u043C\u0438\u043D\u0438\u043C\u0443\u043C 8 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432 \u0438 \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u0431\u0443\u043A\u0432\u0443 \u0432\u0435\u0440\u0445\u043D\u0435\u0433\u043E, \u043D\u0438\u0436\u043D\u0435\u0433\u043E \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430 \u0438 \u0447\u0438\u0441\u043B\u043E \u0438\u043B\u0438 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u044B\u0439 \u0441\u0438\u043C\u0432\u043E\u043B"
            },
            confirm: {
                pattern: "\u0412\u0430\u0448 \u043F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0432\u043A\u043B\u044E\u0447\u0430\u0442\u044C \u043C\u0438\u043D\u0438\u043C\u0443\u043C 8 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432 \u0438 \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u043B\u0430\u0442\u0438\u043D\u0441\u043A\u0443\u044E \u0431\u0443\u043A\u0432\u0443 \u0432\u0435\u0440\u0445\u043D\u0435\u0433\u043E, \u043D\u0438\u0436\u043D\u0435\u0433\u043E \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430 \u0438 \u0447\u0438\u0441\u043B\u043E \u0438\u043B\u0438 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u044B\u0439 \u0441\u0438\u043C\u0432\u043E\u043B"
            },
            address: {
                pattern: "\u041D\u0435\u043F\u0440\u0430\u0432\u0438\u043B\u044C\u043D\u044B\u0439 \u0444\u043E\u0440\u043C\u0430\u0442. \u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0430\u0434\u0440\u0435\u0441 \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \\'\u0443\u043B. \u041F\u0443\u0448\u043A\u0438\u043D\u0430, 13, \u043A\u0432. 37\\'"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents( ".col-sm-5" ).addClass( "has-feedback" );
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !element.next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
            }
        },
        success: function ( label, element ) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !$( element ).next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
        },
        unhighlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
        }
    });
});

$(document).ready( function () {
    $("#changePassword").validate( {
        rules: {
            old_password: "required",
            new_password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
            },
            confirm_password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/,
                equalTo: "#new_password"
            }
        },
        messages: {
            new_password: {
                pattern: "\u0412\u0430\u0448 \u043F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0432\u043A\u043B\u044E\u0447\u0430\u0442\u044C \u043C\u0438\u043D\u0438\u043C\u0443\u043C 8 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432 \u0438 \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u0431\u0443\u043A\u0432\u0443 \u0432\u0435\u0440\u0445\u043D\u0435\u0433\u043E, \u043D\u0438\u0436\u043D\u0435\u0433\u043E \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430 \u0438 \u0447\u0438\u0441\u043B\u043E \u0438\u043B\u0438 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u044B\u0439 \u0441\u0438\u043C\u0432\u043E\u043B"
            },
            confirm_password: {
                pattern: "\u0412\u0430\u0448 \u043F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0432\u043A\u043B\u044E\u0447\u0430\u0442\u044C \u043C\u0438\u043D\u0438\u043C\u0443\u043C 8 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432 \u0438 \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u043B\u0430\u0442\u0438\u043D\u0441\u043A\u0443\u044E \u0431\u0443\u043A\u0432\u0443 \u0432\u0435\u0440\u0445\u043D\u0435\u0433\u043E, \u043D\u0438\u0436\u043D\u0435\u0433\u043E \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430 \u0438 \u0447\u0438\u0441\u043B\u043E \u0438\u043B\u0438 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u044B\u0439 \u0441\u0438\u043C\u0432\u043E\u043B"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents( ".input-group" ).addClass( "has-feedback" );
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !element.next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
            }
        },
        success: function ( label, element ) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !$( element ).next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".input-group" ).addClass( "has-error" ).removeClass( "has-success" );
            $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
        },
        unhighlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".input-group" ).addClass( "has-success" ).removeClass( "has-error" );
            $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
        }
    });
});


