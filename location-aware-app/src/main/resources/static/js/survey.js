var first_survey=document.querySelector("#first_survey")
var second_survey=document.querySelector("#second_survey")
var third_survey=document.querySelector("#third_survey")
var fourth_survey=document.querySelector("#fourth_survey")


var goToStep2Trigger = second_survey
goToStep2Trigger.addEventListener('click', function () {
    scrollToTop();
    setTimeout(function () {
        var step2TabElement = document.querySelector('#step-2-tab');
        var step2Tab = new bootstrap.Tab(step2TabElement);
        step2Tab.show();
    }, 1000)
})

var goToStep3Trigger = third_survey
goToStep3Trigger.addEventListener('click', function () {
    scrollToTop();
    setTimeout(function () {
        var step3TabElement = document.querySelector('#step-3-tab');
        var step3Tab = new bootstrap.Tab(step3TabElement);
        step3Tab.show();
    }, 1000)
})

var gotoStep4Trigger = goToStep4Survey
gotoStep4Trigger.addEventListener('click', function () {
    scrollToTop();
    setTimeout(function () {
        var step3TabElement = document.querySelector('#step-3-tab');
        var step3Tab = new bootstrap.Tab(step3TabElement);
        step3Tab.show();
    }, 1000)
})


function scrollToTop() {
    scroll({
        top: 0,
        behavior: "smooth"
    });
}

var submitButton = document.querySelector('#submitButton');
submitButton.addEventListener('click', function () {
    submitButton.disabled = true;
    submitButton.querySelector('.spinner-border').classList.remove('d-none');
    setTimeout(function () {
        window.location.href = 'results.html';
    }, 2000)
})