mini.ux.IFrameWindow = function () {
    mini.ux.IFrameWindow.superclass.constructor.call(this);    
}
mini.extend(mini.ux.IFrameWindow, mini.ux.Window, {
    title: "IFrame Window",
    url: "",
    initControls:function(){
		 
	}
});
mini.regClass(mini.ux.IFrameWindow, "ux.iframewindow");