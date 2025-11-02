# Render Deployment Guide

## Overview
This guide provides step-by-step instructions for deploying the Student Management App to Render with the fixes applied for authentication and database connectivity.

## Prerequisites
- Active Render account
- PostgreSQL database service on Render
- Updated Student Management App code with deployment fixes

## Required Environment Variables

### Database Configuration
Set these in your Render Web Service environment variables:

```bash
DATABASE_URL=postgresql://username:password@host:port/database
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DDL_AUTO=validate
SHOW_SQL=false
```

**Note**: If you link your web service to a PostgreSQL service on Render, `DATABASE_URL` is provided automatically.

### JWT Configuration
```bash
JWT_SECRET=your-32-character-or-more-secret-key
```

**Generate a secure JWT secret:**
```bash
openssl rand -base64 32
```

## Deployment Steps

### 1. Update Code
Ensure all fixes from the implementation plan are applied:
- Spring Security configuration fixed
- Environment-based database connection
- JWT service with persistent signing key
- Frontend with dynamic API URL

### 2. Deploy to Render

1. **Create Web Service:**
   - Service Type: Web Service
   - Runtime: Docker
   - Dockerfile Path: ./Dockerfile
   - Branch: main (or your deployment branch)

2. **Configure Environment Variables:**
   - Add all required environment variables from above
   - Ensure `DATABASE_URL` points to your Render PostgreSQL service

3. **Set Health Check (Optional):**
   - Health Check Path: `/actuator/health` (if implemented) or `/`
   - Check interval: 30 seconds
   - Timeout: 10 seconds

### 3. Database Setup
If using an external PostgreSQL service:
1. Create database service on Render
2. Add connection string to `DATABASE_URL`
3. Link web service to database service (recommended)

## Verification Steps

### 1. Check Deployment Logs
```bash
# In Render dashboard, view your service logs
# Look for these success indicators:
# - Database connection established
# - No authentication warnings
# - Server started on port 8080
```

### 2. Test API Endpoints
```bash
# Test teacher registration
curl -X POST https://your-app.onrender.com/auth/register-teacher \
  -H "Content-Type: application/json" \
  -d '{"teacherName":"testteacher","password":"testpass"}'

# Test teacher login
curl -X POST https://your-app.onrender.com/auth/login-teacher \
  -H "Content-Type: application/json" \
  -d '{"teacherName":"testteacher","password":"testpass"}'

# Test student registration
curl -X POST https://your-app.onrender.com/auth/register-student \
  -H "Content-Type: application/json" \
  -d '{"studentName":"teststudent","password":"testpass"}'

# Test student login
curl -X POST https://your-app.onrender.com/auth/login-student \
  -H "Content-Type: application/json" \
  -d '{"studentName":"teststudent","password":"testpass"}'
```

### 3. Test Frontend
1. Open your deployed application URL
2. Test teacher registration form
3. Test student registration form
4. Test login forms for both user types
5. Verify redirects work correctly

## Troubleshooting

### Issue: Authentication warnings in logs
**Solution**: Verify that the `authenticationProvider()` bean was removed from `SecurityConfig.java`

### Issue: Database connection failed
**Solution**:
- Check that `DATABASE_URL` is correctly set
- Verify PostgreSQL service is running
- Ensure database credentials are correct

### Issue: "Invalid JWT token" errors
**Solution**:
- Ensure `JWT_SECRET` environment variable is set
- Check that it's persistent across deployments
- Verify secret is at least 32 characters long

### Issue: CORS errors in browser
**Solution**:
- Verify frontend API URL is dynamic (not hardcoded localhost)
- Check that CORS configuration allows all origins for development
- Ensure OPTIONS requests are properly handled

### Issue: 500 Internal Server Error
**Solution**:
- Check Render logs for stack traces
- Verify all environment variables are set
- Ensure database connection is established

## Environment-Specific Configurations

### Local Development
No environment variables needed - uses defaults:
- Database: `host.docker.internal:5432/Demo`
- JWT Secret: Uses built-in default
- API URL: `http://localhost:8080`

### Production (Render)
All environment variables must be set:
- Database: Render PostgreSQL connection string
- JWT Secret: Secure generated secret
- DDL Auto: Set to `validate` for production
- Show SQL: Set to `false` for production

## Success Indicators

Your deployment is successful when:
1. ✅ No authentication configuration warnings in logs
2. ✅ Database connection established to Render PostgreSQL
3. ✅ Teacher and student registration forms work
4. ✅ Teacher and student login forms work
5. ✅ JWT tokens persist across application restarts
6. ✅ Frontend loads and functions correctly on deployed URL
7. ✅ All API endpoints return appropriate responses
8. ✅ Error handling works as expected

## Post-Deployment Maintenance

1. **Monitor logs regularly** for any new warnings or errors
2. **Backup database** regularly through Render dashboard
3. **Update JWT secret** periodically and rotate tokens if needed
4. **Monitor resource usage** and scale as needed