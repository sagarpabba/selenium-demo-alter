/*
 * inspired by this site:http://www.cnblogs.com/zhxhdean/archive/2011/09/21/2184153.html
 */

$.extend($.fn.validatebox.defaults.rules, {  
	
  number: {
        validator: function(value){
            return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
        },
        message: 'you need to input the number'
   },
   age: {//age
                validator: function (value) {
                    return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
                },
                message: 'age should start from 0- 120'
    },
    unnormal: {// validate input is valid
                validator: function (value) {
                    return /.+/i.test(value);
                },
                message: 'the input cannot be empty or other characters'
            },
    minLength: {  
        validator: function(value, param){  
            return value.length >= param[0];  
        },  
        message: 'Please enter at least {0} characters.'  
    },
    betweenLength: {  
        validator: function(value, param){  
            return value.length >= param[0] && value.length <= param[1];  
        },  
        message: 'Please enter at least {0} characters,less than {1} characters.'  
    },
   
    passwordEquals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: 'Password Field do not match you had input:{0}'
    },
    //chinese chracter
     CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: 'please input the chinese chracter'
    },
     phone: {// phone number
                validator: function (value) {
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
             },
                message: 'please use this format:020-88888888'
            },
    //mobile phone
    mobile: {//the value is the input value
        validator: function (value) {
           // var reg = /^1[3|4|5|8|9]\d{9}$/;
            var reg = /^\d{11}$/;
            return reg.test(value);
        },
        message: 'the mobile phone format(11 chracters) you input is incorrect.'
    },
    //the zip code
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: 'the zipcode must be started with 0 and have 6 chracters.'
    },
    //use acccount check
    account: {//
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = 'user name length start from ' + param[0] + ' to ' + param[1] ;
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = 'user name must be number,chracter and _';
                    return false;
                } else {
                    return true;
                }
            }
        }, 
        message: ''
    }
}); 
