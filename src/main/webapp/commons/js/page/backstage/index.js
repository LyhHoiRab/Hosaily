var app = angular.module('app', ['ui.router', 'oc.lazyLoad', 'ngGrid', 'ckeditor', 'ngSanitize', 'ui.bootstrap']);

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
                    basePath + '/commons/js/utils.js',
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
                    basePath + '/commons/js/utils.js',
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
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/media/index.js'
                ]);
            }]
        }
    }).state('media.list', {
        url: '/media/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/media/list/' + $stateParams.organizationId;
        },
        controller: 'mediaListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/media/list.js'
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
                    basePath + '/commons/js/utils.js',
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
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/advisor/index.js'
                ]);
            }]
        }
    }).state('advisor.list', {
        url: '/advisor/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/advisor/list/' + $stateParams.organizationId;
        },
        controller: 'advisorListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/advisor/list.js'
                ]);
            }]
        }
    }).state('advisorAdd', {
        url: '/advisor/add/:organizationId',
        templateUrl: '/page/backstage/advisor/add',
        controller: 'advisorAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
                    basePath + '/commons/js/utils.js',
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
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/tag/index.js'
                ]);
            }]
        }
    }).state('tag.list', {
        url: '/tag/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/tag/list/' + $stateParams.organizationId;
        },
        controller: 'tagListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/tag/list.js'
                ]);
            }]
        }
    }).state('tagAdd', {
        url: '/tag/add/:organizationId',
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
                    basePath + '/commons/js/utils.js',
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
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/post/index.js'
                ]);
            }]
        }
    }).state('post.list', {
        url: '/post/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/post/list/' + $stateParams.organizationId;
        },
        controller: 'postListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/post/list.js'
                ]);
            }]
        }
    }).state('postAdd', {
        url: '/post/add/:organizationId',
        templateUrl: '/page/backstage/post/add',
        controller: 'postAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/course/index.js'
                ]);
            }]
        }
    }).state('course.list', {
        url: '/course/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/course/list/' + $stateParams.organizationId;
        },
        controller: 'courseListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/course/list.js'
                ]);
            }]
        }
    }).state('courseAdd', {
        url: '/course/add/:organizationId',
        templateUrl: '/page/backstage/course/add',
        controller: 'courseAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
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
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/course/sectionEdit.js'
                ]);
            }]
        }
    }).state('customization', {
        url: '/customization',
        templateUrl: '/page/backstage/customization',
        controller: 'customizationController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/customization/index.js'
                ]);
            }]
        }
    }).state('customization.list', {
        url: '/customization/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/customization/list/' + $stateParams.organizationId;
        },
        controller: 'customizationListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/customization/list.js'
                ]);
            }]
        }
    }).state('customizationAdd', {
        url: '/customization/add/:organizationId',
        templateUrl: '/page/backstage/customization/add',
        controller: 'customizationAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/customization/add.js'
                ]);
            }]
        }
    }).state('customizationEdit', {
        url: '/customization/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/customization/edit/' + $stateParams.id;
        },
        controller: 'customizationEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/ckeditor/ckeditor.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/customization/edit.js'
                ]);
            }]
        }
    }).state('user', {
        url: '/user',
        templateUrl: '/page/backstage/user',
        controller: 'userController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/user/index.js'
                ]);
            }]
        }
    }).state('user.list', {
        url: '/user/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/user/list/' + $stateParams.organizationId;
        },
        controller: 'userListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/user/list.js'
                ]);
            }]
        }
    }).state('userEdit', {
        url: '/user/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/user/edit/' + $stateParams.id;
        },
        controller: 'userEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/page/backstage/user/edit.js'
                ]);
            }]
        }
    }).state('authorization', {
        url: '/user/authorization/:accountId',
        templateUrl: function($stateParams){
            return '/page/backstage/user/authorization/' + $stateParams.accountId;
        },
        controller: 'authorizationController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/user/authorization.js'
                ]);
            }]
        }
    }).state('testLibrary', {
        url: '/testLibrary',
        templateUrl: '/page/backstage/testLibrary',
        controller: 'testLibraryController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/testLibrary/index.js'
                ]);
            }]
        }
    }).state('testLibraryAdd', {
        url: '/testLibrary/add',
        templateUrl: '/page/backstage/testLibrary/add',
        controller: 'testLibraryAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/testLibrary/add.js'
                ]);
            }]
        }
    }).state('testLibraryEdit', {
        url: '/testLibrary/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/testLibrary/edit/' + $stateParams.id;
        },
        controller: 'testLibraryEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/testLibrary/edit.js'
                ]);
            }]
        }
    }).state('organization', {
        url: '/organization',
        templateUrl: '/page/backstage/organization',
        controller: 'organizationController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/organization/index.js'
                ]);
            }]
        }
    }).state('organizationAdd', {
        url: '/organization/add',
        templateUrl: '/page/backstage/organization/add',
        controller: 'organizationAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/organization/add.js'
                ]);
            }]
        }
    }).state('organizationEdit', {
        url: '/organization/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/organization/edit/' + $stateParams.id;
        },
        controller: 'organizationEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/organization/edit.js'
                ]);
            }]
        }
    }).state('record', {
        url: '/record',
        templateUrl: '/page/backstage/record',
        controller: 'recordController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/record/index.js'
                ]);
            }]
        }
    }).state('record.list', {
        url: '/record/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/record/list/' + $stateParams.organizationId;
        },
        controller: 'recordListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/record/list.js'
                ]);
            }]
        }
    }).state('recordAdd', {
        url: '/record/add',
        templateUrl: '/page/backstage/record/add',
        controller: 'recordAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/record/add.js'
                ]);
            }]
        }
    }).state('recordEdit', {
        url: '/record/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/record/edit/' + $stateParams.id;
        },
        controller: 'recordEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/record/edit.js'
                ]);
            }]
        }
    }).state('simNumUser', {
        url: '/simNumUser',
        templateUrl: '/page/backstage/simNumUser',
        controller: 'simNumUserController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/simNumUser/index.js'
                ]);
            }]
        }
    }).state('simNumUser.list', {
        url: '/simNumUser/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/simNumUser/list/' + $stateParams.organizationId;
        },
        controller: 'simNumUserListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/simNumUser/list.js'
                ]);
            }]
        }
    }).state('simNumUserAdd', {
        url: '/simNumUser/add',
        templateUrl: '/page/backstage/simNumUser/add',
        controller: 'simNumUserAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/simNumUser/add.js'
                ]);
            }]
        }
    }).state('simNumUserEdit', {
        url: '/simNumUser/edit/:sim',
        templateUrl: function($stateParams){
            return '/page/backstage/simNumUser/edit/' + $stateParams.sim;
        },
        controller: 'simNumUserEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/simNumUser/edit.js'
                ]);
            }]
        }


    }).state('project', {
        url: '/project',
        templateUrl: '/page/backstage/project',
        controller: 'projectController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/project/index.js'
                ]);
            }]
        }
    }).state('project.list', {
        url: '/project/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/project/list/' + $stateParams.organizationId;
        },
        controller: 'projectListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/project/list.js'
                ]);
            }]
        }
    }).state('projectAdd', {
        url: '/project/add',
        templateUrl: '/page/backstage/project/add',
        controller: 'projectAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/project/add.js'
                ]);
            }]
        }
    }).state('projectEdit', {
        url: '/project/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/project/edit/' + $stateParams.id;
        },
        controller: 'projectEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/project/edit.js'
                ]);
            }]
        }


    }).state('question', {
        url: '/question',
        templateUrl: '/page/backstage/question',
        controller: 'questionController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/question/index.js'
                ]);
            }]
        }
    }).state('question.list', {
        url: '/question/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/question/list/' + $stateParams.organizationId;
        },
        controller: 'questionListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/question/list.js'
                ]);
            }]
        }
    }).state('questionAdd', {
        url: '/question/add',
        templateUrl: '/page/backstage/question/add',
        controller: 'questionAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/question/add.js'
                ]);
            }]
        }
    }).state('questionEdit', {
        url: '/question/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/question/edit/' + $stateParams.id;
        },
        controller: 'questionEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/question/edit.js'
                ]);
            }]
        }


    }).state('option', {
        url: '/option',
        templateUrl: '/page/backstage/option',
        controller: 'optionController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/option/index.js'
                ]);
            }]
        }
    }).state('option.list', {
        url: '/option/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/option/list/' + $stateParams.organizationId;
        },
        controller: 'optionListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/option/list.js'
                ]);
            }]
        }
    }).state('optionAdd', {
        url: '/option/add',
        templateUrl: '/page/backstage/option/add',
        controller: 'optionAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/option/add.js'
                ]);
            }]
        }
    }).state('optionEdit', {
        url: '/option/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/option/edit/' + $stateParams.id;
        },
        controller: 'optionEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/ng-uploader/angular-file-upload.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/option/edit.js'
                ]);
            }]
        }





    }).state('courseGroup', {
        url: '/courseGroup',
        templateUrl: '/page/backstage/courseGroup',
        controller: 'courseGroupController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    //basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    //basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/courseGroup/index.js'
                ]);
            }]
        }
    }).state('courseGroup.list', {
        url: '/courseGroup/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/courseGroup/list/' + $stateParams.organizationId;
        },
        controller: 'courseGroupListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/courseGroup/list.js'
                ]);
            }]
        }
    }).state('courseGroupAdd', {
        url: '/courseGroup/add/:organizationId',
        templateUrl: '/page/backstage/courseGroup/add',
        controller: 'courseGroupAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/courseGroup/add.js'
                ]);
            }]
        }
    }).state('courseGroupEdit', {
        url: '/courseGroup/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/courseGroup/edit/' + $stateParams.id;
        },
        controller: 'courseGroupEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/courseGroup/edit.js'
                ]);
            }]
        }
    }).state('webResource', {
        url: '/webResource',
        templateUrl: '/page/backstage/webResource',
        controller: 'webResourceController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/webResource/index.js'
                ]);
            }]
        }
    }).state('webResourceAdd', {
        url: '/webResource/add',
        templateUrl: '/page/backstage/webResource/add',
        controller: 'webResourceAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/webResource/add.js'
                ]);
            }]
        }
    }).state('webResourceEdit', {
        url: '/webResource/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/webResource/edit/' + $stateParams.id;
        },
        controller: 'webResourceEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/webResource/edit.js'
                ]);
            }]
        }
    }).state('appointment', {
        url: '/appointment',
        templateUrl: '/page/backstage/appointment',
        controller: 'appointmentController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/appointment/index.js'
                ]);
            }]
        }
    }).state('appointment.list', {
        url: '/appointment/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/backstage/appointment/list/' + $stateParams.organizationId;
        },
        controller: 'appointmentListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/moment/moment.min.js',
                    basePath + '/commons/js/page/backstage/appointment/list.js'
                ]);
            }]
        }
    }).state('appointmentEdit', {
        url: '/appointment/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/appointment/edit/' + $stateParams.id;
        },
        controller: 'appointmentEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/appointment/edit.js'
                ]);
            }]
        }
    }).state('sales', {
        url: '/sales',
        templateUrl: '/page/backstage/sales',
        controller: 'salesController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/page/backstage/sales/index.js'
                ]);
            }]
        }
    }).state('salesEdit', {
        url: '/sales/edit/:id',
        templateUrl: function($stateParams){
            return '/page/backstage/sales/edit/' + $stateParams.id;
        },
        controller: 'salesEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/backstage/sales/edit.js'
                ]);
            }]
        }
    });
}]);