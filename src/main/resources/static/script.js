// Configuration
const API_BASE = 'http://localhost:8080';
let currentUser = null;
let currentToken = null;

// Utility Functions
function showAlert(message, type = 'error') {
    const alertContainer = document.getElementById('alert-container');
    if (!alertContainer) return;
    
    alertContainer.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
    setTimeout(() => alertContainer.innerHTML = '', 5000);
}

function makeRequest(url, method = 'GET', body = null, useAuth = false) {
    const headers = {
        'Content-Type': 'application/json'
    };

    if (useAuth && currentToken) {
        headers['Authorization'] = `Bearer ${currentToken}`;
    }

    const config = {
        method,
        headers
    };

    if (body) {
        config.body = JSON.stringify(body);
    }

    return fetch(`${API_BASE}${url}`, config);
}

// Form Toggle Functions
function showLoginForm(type) {
    // Update toggle buttons
    document.querySelectorAll('.toggle-btn').forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    // Show/hide forms
    document.querySelectorAll('.login-form').forEach(form => form.classList.remove('active'));
    document.getElementById(`${type}-login`).classList.add('active');
    
    // Clear alerts
    const alertContainer = document.getElementById('alert-container');
    if (alertContainer) alertContainer.innerHTML = '';
}

function showRegisterForm(type) {
    // Update toggle buttons
    document.querySelectorAll('.toggle-btn').forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    // Show/hide forms
    document.querySelectorAll('.login-form').forEach(form => form.classList.remove('active'));
    document.getElementById(`${type}-register`).classList.add('active');
    
    // Clear alerts
    const alertContainer = document.getElementById('alert-container');
    if (alertContainer) alertContainer.innerHTML = '';
}

// Authentication Functions
async function handleTeacherLogin(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const data = {
        teacherName: formData.get('teacherName'),
        password: formData.get('password')
    };

    try {
        const response = await makeRequest('/auth/login-teacher', 'POST', data);
        
        if (response.ok) {
            const result = await response.json();
            currentToken = result.token;
            currentUser = { name: data.teacherName, type: 'teacher' };
            
            // Store in sessionStorage for persistence
            sessionStorage.setItem('token', currentToken);
            sessionStorage.setItem('user', JSON.stringify(currentUser));
            
            showAlert('Login successful! Redirecting to dashboard...', 'success');
            
            // Redirect to dashboard page (you'll need to create this)
            setTimeout(() => {
                window.location.href = 'teacher-dashboard.html';
            }, 1500);
            
        } else {
            const error = await response.text();
            showAlert('Login failed: ' + error, 'error');
        }
    } catch (error) {
        showAlert('Login failed: ' + error.message, 'error');
    }
}

async function handleStudentLogin(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const data = {
        studentName: formData.get('studentName'),
        password: formData.get('password')
    };

    try {
        const response = await makeRequest('/auth/login-student', 'POST', data);
        
        if (response.ok) {
            const result = await response.json();
            currentToken = result.token;
            currentUser = { name: data.studentName, type: 'student' };
            
            // Store in sessionStorage for persistence
            sessionStorage.setItem('token', currentToken);
            sessionStorage.setItem('user', JSON.stringify(currentUser));
            
            showAlert('Login successful! Redirecting to dashboard...', 'success');
            
            // Redirect to dashboard page (you'll need to create this)
            setTimeout(() => {
                window.location.href = 'student-dashboard.html';
            }, 1500);
            
        } else {
            const error = await response.text();
            showAlert('Login failed: ' + error, 'error');
        }
    } catch (error) {
        showAlert('Login failed: ' + error.message, 'error');
    }
}

async function handleTeacherRegister(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const data = {
        teacherName: formData.get('teacherName'),
        password: formData.get('password')
    };

    try {
        const response = await makeRequest('/auth/register-teacher', 'POST', data);
        
        if (response.ok) {
            showAlert('Registration successful! You can now login.', 'success');
            event.target.reset();
            
            // Switch to login form after 2 seconds
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
            
        } else {
            const error = await response.text();
            showAlert('Registration failed: ' + error, 'error');
        }
    } catch (error) {
        showAlert('Registration failed: ' + error.message, 'error');
    }
}

async function handleStudentRegister(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const data = {
        studentName: formData.get('studentName'),
        password: formData.get('password')
    };

    try {
        const response = await makeRequest('/auth/register-student', 'POST', data);
        
        if (response.ok) {
            showAlert('Registration successful! You can now login.', 'success');
            event.target.reset();
            
            // Switch to login form after 2 seconds
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
            
        } else {
            const error = await response.text();
            showAlert('Registration failed: ' + error, 'error');
        }
    } catch (error) {
        showAlert('Registration failed: ' + error.message, 'error');
    }
}

// Dashboard Functions for Multiple Teachers System
async function loadTeacherProfile() {
    try {
        const response = await makeRequest('/teachers/profile', 'GET', null, true);
        if (response.ok) {
            const teacher = await response.json();
            return teacher;
        }
        throw new Error('Failed to load profile');
    } catch (error) {
        console.error('Failed to load teacher profile:', error);
        return null;
    }
}

async function loadMyStudents() {
    try {
        const response = await makeRequest('/teachers/my-students', 'GET', null, true);
        if (response.ok) {
            const students = await response.json();
            return students;
        }
        throw new Error('Failed to load students');
    } catch (error) {
        console.error('Failed to load students:', error);
        return [];
    }
}

// Updated functions for multiple teachers system
async function loadMyTeachers() {
    try {
        const response = await makeRequest('/students/my-teachers', 'GET', null, true);
        if (response.ok) {
            const teachers = await response.json();
            return teachers;
        }
        throw new Error('Failed to load teachers');
    } catch (error) {
        console.error('Failed to load teachers:', error);
        return [];
    }
}

async function loadMyClasses() {
    try {
        const response = await makeRequest('/students/my-classes', 'GET', null, true);
        if (response.ok) {
            const classes = await response.json();
            return classes;
        }
        throw new Error('Failed to load classes');
    } catch (error) {
        console.error('Failed to load classes:', error);
        return [];
    }
}

async function loadClassmatesForClass(joinCode) {
    try {
        const response = await makeRequest(`/students/classmates/${joinCode}`, 'GET', null, true);
        if (response.ok) {
            const classmates = await response.json();
            return classmates;
        }
        throw new Error('Failed to load classmates');
    } catch (error) {
        console.error('Failed to load classmates for class:', error);
        return [];
    }
}

// Deprecated functions (kept for backward compatibility)
async function loadMyTeacher() {
    console.warn('loadMyTeacher() is deprecated. Use loadMyTeachers() instead.');
    try {
        const teachers = await loadMyTeachers();
        return teachers.length > 0 ? teachers[0] : null;
    } catch (error) {
        console.error('Failed to load teacher:', error);
        return null;
    }
}

async function loadClassmates() {
    console.warn('loadClassmates() is deprecated. Use loadClassmatesForClass(joinCode) instead.');
    try {
        const classes = await loadMyClasses();
        if (classes.length > 0) {
            return await loadClassmatesForClass(classes[0].joinCode);
        }
        return [];
    } catch (error) {
        console.error('Failed to load classmates:', error);
        return [];
    }
}

// Logout Function
function logout() {
    currentUser = null;
    currentToken = null;
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    window.location.href = 'index.html';
}

// Check Authentication Status
function checkAuth() {
    const token = sessionStorage.getItem('token');
    const user = sessionStorage.getItem('user');
    
    if (token && user) {
        currentToken = token;
        currentUser = JSON.parse(user);
        return true;
    }
    return false;
}

// Initialize Page
function initializePage() {
    const currentPage = window.location.pathname.split('/').pop();
    
    // Check if user is already logged in
    if (checkAuth()) {
        // If on login/register page and already authenticated, redirect to dashboard
        if (currentPage === 'login.html' || currentPage === 'register.html') {
            if (currentUser.type === 'teacher') {
                window.location.href = 'teacher-dashboard.html';
            } else {
                window.location.href = 'student-dashboard.html';
            }
            return;
        }
    }
    
    // Add event listeners based on current page
    if (currentPage === 'login.html') {
        const teacherForm = document.getElementById('teacher-login-form');
        const studentForm = document.getElementById('student-login-form');
        
        if (teacherForm) {
            teacherForm.addEventListener('submit', handleTeacherLogin);
        }
        if (studentForm) {
            studentForm.addEventListener('submit', handleStudentLogin);
        }
    }
    
    if (currentPage === 'register.html') {
        const teacherRegForm = document.getElementById('teacher-register-form');
        const studentRegForm = document.getElementById('student-register-form');
        
        if (teacherRegForm) {
            teacherRegForm.addEventListener('submit', handleTeacherRegister);
        }
        if (studentRegForm) {
            studentRegForm.addEventListener('submit', handleStudentRegister);
        }
    }
}

// Smooth Scrolling for Anchor Links
function initializeSmoothScrolling() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
}

// Form Validation
function validateForm(form) {
    const inputs = form.querySelectorAll('input[required]');
    let isValid = true;
    
    inputs.forEach(input => {
        if (!input.value.trim()) {
            isValid = false;
            input.style.borderColor = '#dc3545';
        } else {
            input.style.borderColor = '#ccc';
        }
    });
    
    return isValid;
}

// Add input event listeners for real-time validation
function initializeFormValidation() {
    document.querySelectorAll('input[required]').forEach(input => {
        input.addEventListener('input', function() {
            if (this.value.trim()) {
                this.style.borderColor = '#0078d4';
            } else {
                this.style.borderColor = '#ccc';
            }
        });
    });
}

// Page Load Event
document.addEventListener('DOMContentLoaded', function() {
    initializePage();
    initializeSmoothScrolling();
    initializeFormValidation();
    
    // Add loading states to buttons
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function(e) {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = 'Loading...';
                
                // Re-enable button after 3 seconds (fallback)
                setTimeout(() => {
                    submitBtn.disabled = false;
                    submitBtn.innerHTML = submitBtn.dataset.originalText || 'Submit';
                }, 3000);
            }
        });
    });
    
    // Store original button text
    document.querySelectorAll('button[type="submit"]').forEach(btn => {
        btn.dataset.originalText = btn.innerHTML;
    });
});

// Global error handler
window.addEventListener('error', function(e) {
    console.error('Global error:', e.error);
});

// Handle network errors
window.addEventListener('offline', function() {
    showAlert('You are offline. Some features may not work.', 'info');
});

window.addEventListener('online', function() {
    showAlert('You are back online!', 'success');
});