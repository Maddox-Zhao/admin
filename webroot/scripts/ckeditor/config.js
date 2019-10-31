/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';

	config.filebrowserUploadUrl = '/ckeditor/uploader.html';
	config.language = 'zh-cn'; // 配置语言
	config.uiColor = '#FFF'; // 背景颜色
	config.width = '100%'; // 宽度
	config.height = '300px'; // 高度
	config.skin = 'office2003';// 界面v2,kama,office2003
	// config.toolbar = 'Full';// 工具栏风格Full,Basic
	config.toolbar = 'MyToolbar';// 把默认工具栏改为‘MyToolbar’

	config.toolbar_MyToolbar = [
			[ 'Source',  'Preview', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord',
			 'Undo', 'Redo', '-', 'Find', 'Replace'],		
			[ 'Bold', 'Italic', 'Underline', 'Strike' , 'NumberedList', 'BulletedList','-','Image', 'Flash', 'Table'],			
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', 'Link', 'Unlink', 'Anchor' ],
			[  'Styles', 'Format', 'Font', 'FontSize', 'TextColor', 'BGColor' ],
			 ];

};
