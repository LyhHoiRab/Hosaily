app.controller('mediaAddController', function($scope, $state, $http, FileUploader){
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/media/upload',
        queueLimit: 1,
        method: 'POST',
        removeAfterUpload: true
    });

     uploader.filters.push({
         name: 'imageFilter',
         fn: function(item){
             var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
             return 'rm|rmvb|wmv|avi|mp4|mkv|mov|wav|mp3|wma|ogg|acc|wave|ape|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
         }
     });

    uploader.onSuccessItem = function(item, response, status, headers){
        if(response.success){
            alert(response.msg);
            $state.go('mediaEdit', {id : response.result.id});
        }
    };

    uploader.onCancelItem = function(item, response, status, headers){
        
    };

    uploader.onErrorItem = function(item, response, status, headers){
        
    };


    // $scope.getSex = function(){
    //     $http({
    //         url: '/api/1.0/sex/list',
    //         method: 'GET'
    //     }).success(function(res, status, headers, config){
    //         if(res.success){
    //             // $scope.sexs = res.result;
    //         }
    //     }).error(function(response){
    //         // $scope.sexs = [];
    //     });
    // };



    $(document).ready(function () {
        // String path, String url, String originalName, Long size, String md5
        var path;
        // var url;
        var originalName;
        var size;
        var md5;
        $("#fileupload").change(function () {

            date=(new Date()).toUTCString();

            file=$(this).get(0).files[0];
            bucket_name="hsl-video";              //服务名称
            opename="unesmall";                      //操作员账号
            opepass="unesmall123456";                      //操作员密码
            //保存路径
            acc_point="http://v0.api.upyun.com/";
            md5 = SparkMD5.hash("PUT&/"+encodeURI(bucket_name)+"&"+date+"&"+file.size+"&"+SparkMD5.hash(opepass));
            save_as="/course/media/"+md5+'_'+file.name;
            sign=SparkMD5.hash("PUT&/"+encodeURI(bucket_name+save_as)+"&"+date+"&"+file.size+"&"+SparkMD5.hash(opepass));

            // infoHtml = "文件名称:" + file.name + "<br/>";
            // infoHtml+= "文件大小:" + file.size + "<br/>";
            // infoHtml+= "文件类型:" + file.type + "<br/>";

            originalName = file.name;
            size = file.size;

            console.log(sign)
            $("#info").html(infoHtml);
        });
        $("#sendBtn").click(function () {
            var xhr = new XMLHttpRequest();
            if(file.size>5000000000){
                alert('文件不允许大于5000')
                return;
            }
            xhr.upload.onprogress = function (event) {
                if (event.lengthComputable) {
                    var percent = Math.round(event.loaded * 100 / event.total);
                    $("#progress").show();
                    $("#percent").text(percent + "%");
                    $("#bar").width( percent+'%');
                };
            };
            xhr.onload=function(event){
                $("#progress").hide();
                if(xhr.status==200){
                    alert("上传成功");
                    var media_url = 'http://media.ishsls.com'+save_as;
                    console.log(media_url);
                    path = media_url;
                    // $scope.media.url = media_url;

// alert("aaaa22aa");
                    // $http({
                    //     url: 'http://localhost:8080/api/1.0/media/newUpload',
                    //     method: 'POST',
                    //     data: $.param({
                    //         'originalName' :'originalName'
                    //     }),
                    // }).success(function(response){
                    //     if(response.success){
                    //         alert("bbbb");
                    //         $state.go('mediaEdit', {id : response.result.id});
                    //     }
                    // }).error(function(response){
                    //     alert("bbbb");
                    //     alert(response)
                    // });

                    // $state.go('mediaEdit', {id : 'asasdasdas'});



                    $http({
                        url: '/api/1.0/media/newUpload',
                        method: 'POST',
                        data: $.param({
                            'originalName' : originalName,
                            'size' : size,
                            'md5' : md5,
                            'path' : path
                        }),
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).success(function(res, status, headers, config){
                        if(res.success){
                            // $scope.sexs = res.result;
                            // alert(res.result.id);
                            $state.go('mediaEdit', {id : res.result.id});
                        }
                    }).error(function(response){
                        // $scope.sexs = [];
                    });


                    // alert("上传成功222");
                }else{alert("上传失败,代码:"+JSON.parse(xhr.responseText).code);}
            };
            xhr.open('PUT', acc_point+encodeURI(bucket_name+save_as), true);
            xhr.setRequestHeader("Authorization","UpYun "+opename+":"+sign);
            xhr.setRequestHeader("X-Date",date);
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xhr.send(file);
        });
    });







});