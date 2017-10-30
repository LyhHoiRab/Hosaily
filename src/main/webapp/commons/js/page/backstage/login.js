var login = login || {
    getQrParams: function(){
        $.ajax({
            url: '/api/1.0/application/qr',
            type: 'GET',
            dataType: 'JSON',
            data: {token:'hosaily', redirectUrl:'/page/backstage/index'},
            success: function(res){
                if(res.success){
                    login.createQr(res.result.appid, res.result.redirect_uri, res.result.state);
                }
            }
        });
    },

    createQr: function(appid, redirect_uri, state){
        var obj = new WxLogin({
            id: 'qr',
            appid: appid,
            scope: 'snsapi_login',
            redirect_uri: redirect_uri,
            state: state,
            style: 'black',
            href: ''
        });
    }
};

$(function(){
    login.getQrParams();
});