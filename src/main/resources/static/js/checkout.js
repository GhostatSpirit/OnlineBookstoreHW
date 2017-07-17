/**
 * Created by lykav on 7/17/2017.
 */


function setPayment(id){
    $.getJSON(
        "/checkout/payment?userPaymentId=" + id.toString(), {},
        function(data){
            // alert(data.toString());
            // data = $.parseJSON(data.toString());
            $.each(data,function(n, v){
                // alert(n);
                // // alert(v);
                // alert($('input[name=n]').attr("name"));
                // return;

                $('input[name="' + n + '"].payment-form').val(v);
                $('select[name="' + n + '"].payment-form').val(v);
            });
        }
    )
}

function setShipping(id){
    $.getJSON(
        "/checkout/shipping?userShippingId=" + id.toString(), {},
        function(data){
            // alert(data.toString());
            // data = $.parseJSON(data.toString());
            $.each(data,function(n, v){
                // alert(n);
                // // alert(v);
                // alert($('input[name=n]').attr("name"));
                // return;

                $('input[name="shippingAddress.' + n + '"].shipping-form').val(v);
                $('select[name="shippingAddress.' + n + '"].shipping-form').val(v);
            });
        }
    )
}


$(document).ready(function(){

    $(".set-payment-link").on('click', function(){
        // alert($(this).data('user-payment-id'));
        setPayment($(this).data('user-payment-id'));
    });

    $(".set-shipping-link").on('click', function(){
        // alert($(this).data('user-payment-id'));
        setShipping($(this).data('user-shipping-id'));
    });
});