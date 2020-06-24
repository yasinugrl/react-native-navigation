#import "StackControllerDelegate.h"
#import "StackTransitionManager.h"
#import "UIViewController+LayoutProtocol.h"

@implementation StackControllerDelegate {
    RNNEventEmitter* _eventEmitter;
    UIViewController* _presentedViewController;
}

- (instancetype)initWithEventEmitter:(RNNEventEmitter *)eventEmitter {
    self = [super init];
    _eventEmitter = eventEmitter;
    return self;
}

- (void)navigationController:(UINavigationController *)navigationController didShowViewController:(UIViewController *)viewController animated:(BOOL)animated {
    if (_presentedViewController && ![navigationController.viewControllers containsObject:_presentedViewController]) {
        [_presentedViewController screenPopped];
    }
    
    _presentedViewController = viewController;
}

- (id<UIViewControllerAnimatedTransitioning>)navigationController:(UINavigationController *)navigationController
								  animationControllerForOperation:(UINavigationControllerOperation)operation
											   fromViewController:(UIViewController*)fromVC
												 toViewController:(UIViewController*)toVC {
	if (operation == UINavigationControllerOperationPush && toVC.resolveOptions.animations.push.hasCustomAnimation) {
		return [[StackTransitionManager alloc] initWithScreenTransition:toVC.resolveOptions.animations.push bridge:_eventEmitter.bridge fromVC:fromVC toVC:toVC];
	} else if (operation == UINavigationControllerOperationPop && fromVC.resolveOptions.animations.pop.hasCustomAnimation) {
        StackTransitionManager* d = [[StackTransitionManager alloc] initWithScreenTransition:fromVC.resolveOptions.animations.pop bridge:_eventEmitter.bridge fromVC:fromVC toVC:toVC];
        return [d isValid] ? d : nil;
	} else {
		return nil;
	}
	
	return nil;
}


@end
