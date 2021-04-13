
function initNavigationMock() {
    jest.mock('./../adapters/NativeCommandsSender', () => require('./NativeCommandsSender.tsx'));
    jest.mock('./../adapters/NativeEventsReceiver', () => require('./NativeEventsReceiver.ts'));
}

export const ApplicationGenerator = () => {
    return require('./Application').Application;
};

export {
    initNavigationMock
}