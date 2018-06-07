/**
 * Created by Administrator on 2017/12/7 0007.
 */
$(document).ready(function () {
    //初始页查询
    loadTransaction(1);
});

function hrefTransaction(trxId) {
    window.location.href="detail?trxId="+trxId;
}

function loadTransaction(pageno) {
    var address = $("#contractId").html();
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
                if (o % 2 == 0) {
                    transaction = $('<tr><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+data[o].trxTypeStr+'</td><td>'+data[o].amountStr+'</td><td>'+data[o].trxTimeStr+'</td></tr>');
                } else {
                    transaction = $('<tr class="trbg"><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+data[o].trxTypeStr+'</td><td>'+data[o].amountStr+'</td><td>'+data[o].trxTimeStr+'</td></tr>');
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