/**
 * Created by Administrator on 2017/12/7 0007.
 */
$(document).ready(function () {
    //初始页查询
    loadTransaction(1);
        var height=$(".contentmain").height();
    if(height<520){
        $(".footer").css({
            'position':'fixed',
            'bottom':'0',
            'width':'100%'
        })
    }
});

function hrefTransaction(trxId) {
    window.location.href="detail?trxId="+trxId;
}

function loadTransaction(pageno) {
    var address = $("#address").html();
    $.ajax({
        type: "POST",
        url: "queryTransactionList",
        dataType: "json",
        data:{"address":address,
               "page":pageno},
        success: function(result, textStatus) {
            var transactions = $("#transactions");
            transactions.empty();

            var data = result.rows;
            for (var o in data) {
                var transaction;
                var fromAccount = '';
                if(data[o].fromAccounts!=null && data[o].fromAccounts.length>0){
                    for(var i=0;i<data[o].fromAccounts.length;i++){
                        fromAccount = fromAccount + '<div>'+ data[o].fromAccounts[i] + '</div>';
                    }
                }
                if (o % 2 == 0) {
                    transaction = $('<tr><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+ data[o].fromAccount +'</td><td>'+data[o].toAccount+'</td><td>'+data[o].amountStr+'</td></tr>');
                } else {
                    transaction = $('<tr class="trbg"><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+ data[o].fromAccount +'</td><td>'+data[o].toAccount+'</td><td>'+data[o].amountStr+'</td></tr>');
                }

                transactions.append(transaction);
            }

            //加载页码
            $("#page").paging({
                pageNo:pageno,
                totalPage: result.pages,
                totalSize: result.total,
                callback: function(num) {
                    loadTransaction(num);
                }
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}