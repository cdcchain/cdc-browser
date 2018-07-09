/**
 * Created by Administrator on 2017/12/7 0007.
 */

function hrefContractDetail(contractId) {
    window.location.href="contractDetail?contractId="+contractId;
}

function loadTransaction(pageno) {
    var keyword = $("#formInput").val();
    if(keyword == "  合约名称 / 合约ID"){
        keyword="";
    }
    $.ajax({
        type: "POST",
        url: "/queryContractList",
        dataType: "json",
        data:{"keyword":keyword,
            "page":pageno},
        success: function(result, textStatus) {
            var contracts = $("#contracts");
            contracts.empty();

            var data = result.rows;
            for (var o in data) {
                var transaction;
                if (o % 2 == 0) {
                    transaction = $('<tr><td><a onclick="hrefContractDetail(\''+data[o].contractId+'\')">'+data[o].contractId+'<a/></td><td>'+data[o].name+'</td><td>'+data[o].balanceStr+'</td><td>'+data[o].regTimeStr+'</td></tr>');
                } else {
                    transaction = $('<tr class="trbg"><td><a onclick="hrefContractDetail(\''+data[o].contractId+'\')">'+data[o].contractId+'<a/></td><td>'+data[o].name+'</td><td>'+data[o].balanceStr+'</td><td>'+data[o].regTimeStr+'</td></tr>');
                }

                contracts.append(transaction);
            }

            //加载页码
            $("#page").paging({
                "keyword":keyword,
                "pageNo":pageno,
                "totalPage": result.pages,
                "totalSize": result.total,
                callback: function(num) {
                    loadTransaction(num);
                }
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

function tipWord(jQueryRule,defaultWord,preColor,afterColor){
    $(jQueryRule).attr("flag",1);
    $(jQueryRule).css("color",preColor);
    $(jQueryRule).val(defaultWord);

    $(jQueryRule).focus(
        function(){
            if($(this).attr("flag")==null||$(this).attr("flag")==1){
                $(this).attr("flag",2);
                $(this).css("color",afterColor);
                $(this).val("");
            }
        }
    );
    $(jQueryRule).blur(function(){
            if($(this).val()==""&&$(this).attr("flag")==2){
                $(this).attr("flag",1);
                $(this).css("color",preColor);
                $(this).val(defaultWord);
            }

        }
    );
}

$(document).ready(function () {
    tipWord("#formInput", "  合约名称 / 合约ID", "rgb(105,105,105)", "rgb(0,0,0)");
    //初始页查询
    loadTransaction(1);
});

//点击查询
function search(){
    loadTransaction(1);
}