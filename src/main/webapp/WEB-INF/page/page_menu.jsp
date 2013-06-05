<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function() {
	$("#west-grid").jqGrid({
		height: "auto",
		autowidth: true,
		treeGrid: true,
		treeGridModel: 'nested',
		pager: false,
		ExpandColumn: "name",
		ExpandColClick: true,
		treeIcons: {leaf:'ui-icon-document-b'},
		datatype: "json",
		jsonReader:{
			repeatitems: false
		},
		rowNum: 200,
		caption: "",
		url: "${base}/system/menu/dispMenu",
		colNames: ["id","功能菜单","url","description"],
		colModel: [
			{name: "id",width:0,hidden:true},
			{name: "name", width:180, resizable: false, sortable:false},
			{name: "url",width:0,hidden:true},
			{name: "description",width:0,hidden:true}
		],
		onSelectRow: function(id) {
			var treedata = $(this).getRowData(id);
			if(treedata.isLeaf=="true") {
				//treedata.url
				var st = "#t"+treedata.id;
				if($(st).html() != null ) {
					maintab.tabs('select',st);
				} else {
					maintab.tabs('add',st, treedata.name);
					//$(st,"#tabs").load(treedata.url);
					$.ajax({
						url: '${base}/'+treedata.url,
						data:{catid: treedata.id},
						cache: false,
						type: "GET",
						//dataType: "html",
						beforeSend: function (xhr) {
							//$(st,"#tabs").height(100).addClass("tabpreloading");
							$(st,"#tabs").css("text-align","center").html("<img src='res/images/loading.gif' border=0 />");
						},
						complete : function (req, err) {
							//$(st,"#tabs").removeClass("tabpreloading").append(req.responseText);
							$(st,"#tabs").empty().html(req.responseText);
							$("input:button,input:submit,input:reset").button();
						}
					});
					
					//$(st,"#tabs").html("<iframe width='100%' height='400' border='0' frameborder='0' scrolling='no' SCROLLING=NO src='"+ treedata.url +"'></iframe>")
					/* $(st,"#tabs").load("${base}/"+treedata.url,function(){
						// 载入网页后要执行的语句:
						$("input:button,input:submit,input:reset").button();
					}); */
				}
			}
		}
	});
});
</script>
<table id="west-grid"></table>