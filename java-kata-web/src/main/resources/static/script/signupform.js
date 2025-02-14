// Email 인증 관련 로직
const emailInput = document.getElementById('email')
const sendVerificationButton = document.getElementById('send-verification-code-btn')
const verificationCodeSection = document.getElementById('verification-code-section')
const verificationCodeInput = document.getElementById('verification-code')
const checkVerificationButton = document.getElementById('check-verification-code-btn')
const submitButton = document.getElementById('submit-btn')

function validateEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}

function toggleButtonState() {
    const isValidEmail = validateEmail(emailInput.value);
    sendVerificationButton.disabled = !isValidEmail;
}

function sendVerificationCode() {
    // TODO 수정 필요
    const baseUrl = 'http://localhost:8080'
    const url = '/api/auth/email/verificationCode'

    const email = emailInput.value

    fetch(`${baseUrl}${url}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({email: email})
    }).then(response => {
        // TODO REMOVE
        console.log(response);

        if (!response.ok) {
            alert('이메일 발송에 실패했습니다.');
        } else {
            alert('인증 코드가 이메일로 발송되었습니다.');
            verificationCodeSection.classList.toggle('hidden');
        }
    })
}

function checkVerificationCode() {
    // TODO 수정 필요
    const baseUrl = 'http://localhost:8080'
    const url = '/api/auth/email/verificationCode/check'

    const email = emailInput.value

    fetch(`${baseUrl}${url}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({email: email, verificationCode: verificationCodeInput.value})
    }).then(response => {
        if (!response.ok) {
            alert('인증번호 검증이 실패했습니다.');
        } else {
            alert('인증번호 검증이 완료되었습니다.');
            verificationCodeSection.classList.toggle('hidden');
            submitButton.disabled = false;
        }
    })
}

checkVerificationButton.addEventListener('click', checkVerificationCode);
sendVerificationButton.addEventListener('click', sendVerificationCode);
emailInput.addEventListener('input', toggleButtonState);

// 초기 버튼 상태 설정
toggleButtonState();

