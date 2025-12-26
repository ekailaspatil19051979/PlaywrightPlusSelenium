@echo off
REM Unified test runner for Playwright (TypeScript) and Selenium (Java) with Allure reporting

REM Set directories
set PLAYWRIGHT_DIR=playwright-tests
set SELENIUM_DIR=selenium-tests
set REPORT_DIR=shared-reporting
set PLAYWRIGHT_RESULTS=%REPORT_DIR%\allure-results-playwright
set SELENIUM_RESULTS=%REPORT_DIR%\allure-results-selenium
set ALLURE_REPORT=%REPORT_DIR%\allure-report

REM Clean previous results
if exist "%PLAYWRIGHT_RESULTS%" rmdir /s /q "%PLAYWRIGHT_RESULTS%"
if exist "%SELENIUM_RESULTS%" rmdir /s /q "%SELENIUM_RESULTS%"
if exist "%ALLURE_REPORT%" rmdir /s /q "%ALLURE_REPORT%"

REM Run Playwright tests
pushd "%~dp0%PLAYWRIGHT_DIR%"
if exist node_modules\.bin\allure-playwright (
    npx playwright test --reporter=line,allure-playwright || exit /b 1
) else (
    echo Installing Playwright dependencies...
    call npm ci || exit /b 1
    npx playwright test --reporter=line,allure-playwright || exit /b 1
)
if exist allure-results (
    mkdir "%~dp0%PLAYWRIGHT_RESULTS%"
    xcopy /e /i /y allure-results "%~dp0%PLAYWRIGHT_RESULTS%"
)
popd

REM Run Selenium tests
pushd "%~dp0%SELENIUM_DIR%"
if exist target rmdir /s /q target
call mvn clean test -Dheadless=true || exit /b 1
if exist target\allure-results (
    mkdir "%~dp0%SELENIUM_RESULTS%"
    xcopy /e /i /y target\allure-results "%~dp0%SELENIUM_RESULTS%"
)
popd


REM Merge Allure results
if not exist "%ALLURE_REPORT%" mkdir "%ALLURE_REPORT%"
if exist "%PLAYWRIGHT_RESULTS%" xcopy /e /i /y "%PLAYWRIGHT_RESULTS%" "%ALLURE_REPORT%" >nul
if exist "%SELENIUM_RESULTS%" xcopy /e /i /y "%SELENIUM_RESULTS%" "%ALLURE_REPORT%" >nul
if exist "%~dp0selenium-tests\target\allure-results" xcopy /e /i /y "%~dp0selenium-tests\target\allure-results" "%ALLURE_REPORT%" >nul

REM Generate Allure report
where allure >nul 2>nul
if %errorlevel%==0 (
    allure generate "%ALLURE_REPORT%" --clean -o "%ALLURE_REPORT%" || exit /b 1
    echo Allure report generated at %ALLURE_REPORT%
    REM Optionally open the report automatically
    REM start "" "%ALLURE_REPORT%\index.html"
) else (
    echo Allure commandline not found. Please install Allure CLI and add it to your PATH.
    exit /b 1
)

REM Open Allure report (optional)
REM start "" "%ALLURE_REPORT%\index.html"

echo All tests and reporting complete.
exit /b 0
