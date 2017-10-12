var app = angular.module('app', ['ui.router', 'oc.lazyLoad', 'ngGrid']);

app.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($controllerProvider, $compileProvider, $filterProvider, $provide){
    app.controller = $controllerProvider.register;
    app.directive  = $compileProvider.directive;
    app.filter     = $filterProvider.register;
    app.factory    = $provide.factory;
    app.service    = $provide.service;
    app.constant   = $provide.constant;
    app.value      = $provide.value;

}]).run(['$rootScope', '$state', '$stateParams', function($rootScope, $state, $stateParams){
    $rootScope.$state       = $state;
    $rootScope.$stateParams = $stateParams; 

}]).config(['$stateProvider', function($stateProvider){
	$stateProvider.state('application', {
		url: '/application',
		templateUrl: '/page/backstage/application',
		controller: 'applicationController',
		resolve: {
			deps: ['$ocLazyLoad', function($ocLazyLoad){
				return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/application/index.js'
				]);
			}]
		}
	}).state('applicationAdd', {
        url: '/application/add',
        templateUrl: '/page/backstage/application/add',
        controller: 'applicationAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/application/add.js'
                ]);
            }]
        }
    }).state('applicationEdit', {
        url: '/application/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/application/edit/' + $stateParams.id;
        },
        controller: 'applicationEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/application/edit.js'
                ]);
            }]
        }
    }).state('level', {
        url: '/level',
        templateUrl: '/page/backstage/level',
        controller: 'levelController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/level/index.js'
                ]);
            }]
        }
    }).state('levelAdd', {
        url: '/level/add',
        templateUrl: '/page/backstage/level/add',
        controller: 'levelAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/level/add.js'
                ]);
            }]
        }
    }).state('levelEdit', {
        url: '/level/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/level/edit/' + $stateParams.id;
        },
        controller: 'levelEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/level/edit.js'
                ]);
            }]
        }
    }).state('media', {
        url: '/media',
        templateUrl: '/page/backstage/media',
        controller: 'mediaController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/media/index.js'
                ]);
            }]
        }
    }).state('mediaAdd', {
        url: '/media/add',
        templateUrl: '/page/backstage/media/add',
        controller: 'mediaAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/media/add.js'
                ]);
            }]
        }
    }).state('mediaEdit', {
        url: '/media/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/media/edit/' + $stateParams.id;
        },
        controller: 'mediaEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/media/edit.js'
                ]);
            }]
        }
    }).state('advisor', {
        url: '/advisor',
        templateUrl: '/page/backstage/advisor',
        controller: 'advisorController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/advisor/index.js'
                ]);
            }]
        }
    }).state('advisorAdd', {
        url: '/advisor/add',
        templateUrl: '/page/backstage/advisor/add',
        controller: 'advisorAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/advisor/add.js'
                ]);
            }]
        }
    }).state('advisorEdit', {
        url: '/advisor/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/advisor/edit/' + $stateParams.id;
        },
        controller: 'advisorEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/advisor/edit.js'
                ]);
            }]
        }
    }).state('tag', {
        url: '/tag',
        templateUrl: '/page/backstage/tag',
        controller: 'tagController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/tag/index.js'
                ]);
            }]
        }
    }).state('tagAdd', {
        url: '/tag/add',
        templateUrl: '/page/backstage/tag/add',
        controller: 'tagAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/tag/add.js'
                ]);
            }]
        }
    }).state('tagEdit', {
        url: '/tag/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/tag/edit/' + $stateParams.id;
        },
        controller: 'tagEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/tag/edit.js'
                ]);
            }]
        }
    }).state('post', {
        url: '/post',
        templateUrl: '/page/backstage/post',
        controller: 'postController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/post/index.js'
                ]);
            }]
        }
    }).state('postAdd', {
        url: '/post/add',
        templateUrl: '/page/backstage/post/add',
        controller: 'postAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/post/add.js'
                ]);
            }]
        }
    }).state('postEdit', {
        url: '/post/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/post/edit/' + $stateParams.id;
        },
        controller: 'postEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/post/edit.js'
                ]);
            }]
        }
    }).state('course', {
        url: '/course',
        templateUrl: '/page/backstage/course',
        controller: 'courseController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/course/index.js'
                ]);
            }]
        }
    }).state('courseAdd', {
        url: '/course/add',
        templateUrl: '/page/backstage/course/add',
        controller: 'courseAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/add.js'
                ]);
            }]
        }
    }).state('courseEdit', {
        url: '/course/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/course/edit/' + $stateParams.id;
        },
        controller: 'courseEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/edit.js'
                ]);
            }]
        }
    }).state('chapterAdd', {
        url: '/course/add/chapter/:parentId',
        templateUrl: function($stateParams){
            return '/page/backstage/course/add/chapter/' + $stateParams.parentId;
        },
        controller: 'chapterAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/chapterAdd.js'
                ]);
            }]
        }
    }).state('chapterEdit', {
        url: '/chapter/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/course/edit/chapter/' + $stateParams.id;
        },
        controller: 'chapterEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/chapterEdit.js'
                ]);
            }]
        }
    }).state('sectionAdd', {
        url: '/course/add/section/:parentId',
        templateUrl: function($stateParams){
            return '/page/backstage/course/add/section/' + $stateParams.parentId;
        },
        controller: 'sectionAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/sectionAdd.js'
                ]);
            }]
        }
    }).state('sectionEdit', {
        url: '/section/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/course/edit/section/' + $stateParams.id;
        },
        controller: 'sectionEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.css',
                    basePath + '/commons/js/plugin/ueditor/ueditor.min.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.all.min.js',
                    basePath + '/commons/js/plugin/ueditor/ueditor.config.js',
                    basePath + '/commons/js/plugin/bootstrap-select/bootstrap-select.min.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/sectionEdit.js'
                ]);
            }]
        }
    });
}]);