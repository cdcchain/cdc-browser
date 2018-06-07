
function hrefBlock(blockId) {
    window.location.href="block.do?blockId="+blockId;
}

function hrefTransaction(trxId) {
    window.location.href="detail.do?trxId="+trxId;
}

function hrefBlockNum(blockNum) {
    window.location.href="blockNum.do?blockNum="+blockNum;
}
function hrefAddress(address) {
    window.location.href="transactionList?address="+address;
}

function search() {
    var keyword = $("#formInput").val();
    if(keyword!=null && keyword !="" && RegExp(/^0x[a-fA-F0-9]{40}$/).test(keyword)){
        hrefAddress(keyword);
        return;
    }
    if (keyword !="") {
        $.ajax({
            type: "POST",
            url: "searchBlock",
            data: {"keyword":keyword},
            dataType: "json",
            success: function(data, textStatus) {
                var retCode = data.key;
                if ('fail' == retCode) {
                    alert("未查询到结果");
                }
                if ("block" == retCode) {
                    hrefBlock(data.values);
                }
                if ("blockNum" == retCode) {
                    hrefBlockNum(data.values);
                }
                if ("trx" == retCode) {
                    hrefTransaction(data.values);
                }
            },error: function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    } else {
        alert("对不起请输入搜索内容");
    }
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

function loadStatic() {
    $.ajax({
        type: "POST",
        url: "getStatis",
        dataType: "json",
        success: function(data, textStatus) {
            $("#transCount").empty().append(data.trxCount);
            $("#transAmount").empty().append(data.allTrxAmount);
            $("#transFee").empty().append(data.allTrxFee);
            $("#transAveHour").empty().append(data.trxLatestHour);
            $("#maxHourCount").empty().append(data.trxMaxHour);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

function loadBlock() {
    $.ajax({
        type: "get",
        url: "blocksInfo",
        dataType: "json",
        success: function(data) {
            var blocks = $("#blocks");
            blocks.empty();
            for (var o in data) {
                var block;
                if (o % 2 == 0) {
                    block = $('<tr><td><a onclick="hrefBlock(\''+data[o].blockId+'\')">'+data[o].blockNum+'</a></td><td>'+data[o].blockTimeStr+'</td><td>'+data[o].trxNum+'</td><td>'+data[o].amountStr+'</td><td>'+data[o].signee+'</td><td>'+data[o].blockSize+'</td></tr>');
                } else {
                    block = $('<tr class="trbg"><td><a onclick="hrefBlock(\''+data[o].blockId+'\')">'+data[o].blockNum+'</a></td><td>'+data[o].blockTimeStr+'</td><td>'+data[o].trxNum+'</td><td>'+data[o].amountStr+'</td><td>'+data[o].signee+'</td><td>'+data[o].blockSize+'</td></tr>');
                }
                blocks.append(block);
            }
        }
    });
}

function loadTransaction() {
    $.ajax({
        type: "POST",
        url: "getTrxance",
        dataType: "json",
        success: function(data, textStatus) {
            var transactions = $("#transactions");
            transactions.empty();
            for (var o in data) {
                var transaction;
                if (o % 2 == 0) {
                    transaction = $('<tr><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+data[o].disTime+'</td><td>'+data[o].amountStr+'</td></tr>');
                } else {
                    transaction = $('<tr class="trbg"><td><a onclick="hrefTransaction(\''+data[o].trxId+'\')">'+data[o].trxId+'<a/></td><td>'+data[o].disTime+'</td><td>'+data[o].amountStr+'</td></tr>');
                }

                transactions.append(transaction);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

function timework() {
    loadStatic();
    loadBlock();
    loadTransaction();
}

$(document).ready(function () {

    tipWord("#formInput", " 区块高度、区块哈希、交易ID、地址", "rgb(227,233,247)", "rgb(255, 255, 255)");

    setInterval(function() {
        timework();
    }, 10000)

    timework(); // 初始化
});