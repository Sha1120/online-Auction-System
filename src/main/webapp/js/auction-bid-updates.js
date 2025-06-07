// WebSocket connection
const socket = new WebSocket('ws://' + window.location.host + '/online-Auction-System/bidUpdates');

socket.onopen = function () {
    console.log('WebSocket connected');
};

socket.onmessage = function (event) {
    showToast(event.data);
};

socket.onclose = function () {
    console.log('WebSocket disconnected');
};

socket.onerror = function (error) {
    console.error('WebSocket error:', error);
};

// Pop-up (toast) notification function
function showToast(message) {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.textContent = message;
    toast.style.background = 'rgba(40,40,40,0.95)';
    toast.style.color = '#fff';
    toast.style.padding = '16px 24px';
    toast.style.marginTop = '12px';
    toast.style.borderRadius = '6px';
    toast.style.boxShadow = '0 2px 8px rgba(0,0,0,0.3)';
    toast.style.fontSize = '16px';
    toast.style.opacity = '1';
    toast.style.transition = 'opacity 0.5s';

    container.appendChild(toast);

    // Auto-remove after 3.5 seconds
    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => {
            container.removeChild(toast);
        }, 500);
    }, 3500);
}