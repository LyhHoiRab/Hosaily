app.controller('mediaAddController', function($scope, $state, FileUploader){
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
});