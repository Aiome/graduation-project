/**
 * Created by Aiome on 2018/5/1.
 */
//链接跳转问题
mui('body').on('tap', 'a', function() {
    if ($(this).hasClass('miao-start')){
        document.location.href = this.href;
    }else{
        alert("未开始");
    }

});