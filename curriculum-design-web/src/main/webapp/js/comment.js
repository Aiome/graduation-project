 //链接跳转问题
mui('body').on('tap', 'a', function() {
	document.location.href = this.href;
});
 //头部导航效果
window.onload = function() {
	  var mySwiper1 = new Swiper('#header',{
		  freeMode : true,
		  slidesPerView : 'auto',
	  });	  
}
//活动滚动效果
var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    slidesPerView: 3,
    paginationClickable: true,
    spaceBetween: 30
});
//点击更多分类商品
 $(function(){
	$(".nav-fix-btn").click(function(){
		$(".mui-backdrop").show();
		$(".cate-all").slideDown();
	});
	$(".nav-fix-close").click(function(){
		$(".mui-backdrop").hide();
		$(".cate-all").slideUp();
	});
});

//点击购买弹出遮罩
$(function(){
	$(".fix-buy-btn").click(function(){
		$(".mui-backdrop").show();
		$(".g-detail").slideDown();
	})
	$(".g-detail-close").click(function(){
		$(".mui-backdrop").hide();
		$(".g-detail").slideUp();
	})
})
$(function(){
	$(".g-detail-color , .g-detail-size").each(function(){
		var i=$(this);
		var p=i.find("ul > li");
		p.click(function(){
			if(!!$(this).hasClass("current")){
				$(this).removeClass("current");
			}
			else{
				$(this).addClass("current").siblings("li").removeClass("current");
			}
		})
	})
})

//服务说明
$(function(){
	$(".server-btn").click(function(){
		$(".mui-backdrop").show();
		$(".server").slideDown();
	})
	$(".server .g-detail-close").click(function(){
		$(".mui-backdrop").hide();
		$(".server").slideUp();
	})
	$(".yhq-btn").click(function(){
		$(".mui-backdrop").show();
		$(".yhqhide").slideDown();
	})
	$(".yhqhide .g-detail-close").click(function(){
		$(".mui-backdrop").hide();
		$(".yhqhide").slideUp();
	})
});
//关闭拼团须知
$(function(){
	$(".quit").click(function(){
		$(".g-instructions").hide();
	})
})



        