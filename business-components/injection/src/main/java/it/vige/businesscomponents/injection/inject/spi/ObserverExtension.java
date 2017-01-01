package it.vige.businesscomponents.injection.inject.spi;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.ProcessProducerField;
import javax.enterprise.inject.spi.ProcessProducerMethod;
import javax.enterprise.inject.spi.ProcessSessionBean;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.ProducerFactory;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.inject.Named;

public class ObserverExtension implements Extension {

	private static final Logger logger = getLogger(ObserverExtension.class.getName());

	private int beforeBeanDiscovery;
	private int processAnnotatedType;
	private int processAnnotatedTypeWithAnnotations;
	private int afterBeanDiscovery;
	private int processBean;
	private int processBeanAttributes;
	private int processInjectionPoint;
	private int processInjectionTarget;
	private int processManagedBean;
	private int processObserverMethod;
	private int processProducer;
	private int processProducerField;
	private int processProducerMethod;
	private int processSessionBean;
	private int processSyntheticAnnotatedType;
	private int producer;
	private int producerFactory;

	private int afterDeploymentValidation;
	private int afterTypeDiscovery;
	private int annotated;
	private int annotatedCallable;
	private int annotatedConstructor;
	private int annotatedField;
	private int annotatedMember;
	private int annotatedMethod;
	private int annotatedParameter;
	private int annotatedType;
	private int bean;
	private int beanAttributes;
	private int beanManager;
	private int beforeShutdown;
	private int CDIProvider;
	private int decorator;
	private int eventMetadata;
	private int extension;
	private int injectionPoint;
	private int injectionTarget;
	private int injectionTargetFactory;
	private int interceptor;
	private int observerMethod;
	private int passivationCapable;

	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
		logger.info("beginning the scanning process");
		beforeBeanDiscovery++;
	}

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		logger.info("scanning type: " + pat.getAnnotatedType().getJavaClass().getName());
		processAnnotatedType++;
	}

	<T> void processAnnotatedTypeWithAnnotations(
			@Observes @WithAnnotations({ Named.class }) ProcessAnnotatedType<T> pat) {
		logger.info("scanning type: " + pat.getAnnotatedType().getJavaClass().getName());
		processAnnotatedTypeWithAnnotations++;
	}

	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
		logger.info("finished the scanning process");
		afterBeanDiscovery++;
	}

	<T> void processBean(@Observes ProcessBean<T> pb) {
		logger.info("finished the scanning process");
		processBean++;
	}

	<T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> pb) {
		logger.info("finished the scanning process");
		processBeanAttributes++;
	}

	<K, V> void processInjectionPoint(@Observes ProcessInjectionPoint<K, V> pip) {
		logger.info("finished the scanning process");
		processInjectionPoint++;
	}

	<T> void processInjectionTarget(@Observes ProcessInjectionTarget<T> pit) {
		logger.info("finished the scanning process");
		processInjectionTarget++;
	}

	<T> void processManagedBean(@Observes ProcessManagedBean<T> pmb) {
		logger.info("finished the scanning process");
		processManagedBean++;
	}

	<K, V> void processObserverMethod(@Observes ProcessObserverMethod<K, V> pom) {
		logger.info("finished the scanning process");
		processObserverMethod++;
	}

	<K, V> void processProducer(@Observes ProcessProducer<K, V> pp) {
		logger.info("finished the scanning process");
		processProducer++;
	}

	<K, V> void processProducerField(@Observes ProcessProducerField<K, V> ppf) {
		logger.info("finished the scanning process");
		processProducerField++;
	}

	<K, V> void processProducerMethod(@Observes ProcessProducerMethod<K, V> ppm) {
		logger.info("finished the scanning process");
		processProducerMethod++;
	}

	<T> void processSessionBean(@Observes ProcessSessionBean<T> psb) {
		logger.info("finished the scanning process");
		processSessionBean++;
	}

	<T> void processSyntheticAnnotatedType(@Observes ProcessSyntheticAnnotatedType<T> psa) {
		logger.info("finished the scanning process");
		processSyntheticAnnotatedType++;
	}

	<T> void producer(@Observes Producer<T> p) {
		logger.info("finished the scanning process");
		producer++;
	}

	<T> void producerFactory(@Observes ProducerFactory<T> pf) {
		logger.info("finished the scanning process");
		producerFactory++;
	}

	void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
		logger.info("finished the scanning process");
		afterDeploymentValidation++;
	}

	void afterTypeDiscovery(@Observes AfterTypeDiscovery atd) {
		logger.info("finished the scanning process");
		afterTypeDiscovery++;
	}

	void annotated(@Observes Annotated a) {
		logger.info("finished the scanning process");
		annotated++;
	}

	<T> void annotatedCallable(@Observes AnnotatedCallable<T> ac) {
		logger.info("finished the scanning process");
		annotatedCallable++;
	}

	<T> void annotatedConstructor(@Observes AnnotatedConstructor<T> ac) {
		logger.info("finished the scanning process");
		annotatedConstructor++;
	}

	<T> void annotatedField(@Observes AnnotatedField<T> af) {
		logger.info("finished the scanning process");
		annotatedField++;
	}

	<T> void annotatedMember(@Observes AnnotatedMember<T> am) {
		logger.info("finished the scanning process");
		annotatedMember++;
	}

	<T> void annotatedMethod(@Observes AnnotatedMethod<T> am) {
		logger.info("finished the scanning process");
		annotatedMethod++;
	}

	<T> void annotatedParameter(@Observes AnnotatedParameter<T> ap) {
		logger.info("finished the scanning process");
		annotatedParameter++;
	}

	<T> void annotatedType(@Observes AnnotatedType<T> at) {
		logger.info("finished the scanning process");
		annotatedType++;
	}

	<T> void bean(@Observes Bean<T> b) {
		logger.info("finished the scanning process");
		bean++;
	}

	<T> void beanAttributes(@Observes BeanAttributes<T> ba) {
		logger.info("finished the scanning process");
		beanAttributes++;
	}

	void beanManager(@Observes BeanManager bm) {
		logger.info("finished the scanning process");
		beanManager++;
	}

	void beforeShutdown(@Observes BeforeShutdown bs) {
		logger.info("finished the scanning process");
		beforeShutdown++;
	}

	void CDIProvider(@Observes CDIProvider cp) {
		logger.info("finished the scanning process");
		CDIProvider++;
	}

	<T> void decorator(@Observes Decorator<T> d) {
		logger.info("finished the scanning process");
		decorator++;
	}

	void eventMetadata(@Observes EventMetadata em) {
		logger.info("finished the scanning process");
		eventMetadata++;
	}

	void extension(@Observes Extension e) {
		logger.info("finished the scanning process");
		extension++;
	}

	void injectionPoint(@Observes InjectionPoint ip) {
		logger.info("finished the scanning process");
		injectionPoint++;
	}

	<T> void injectionTargetFactory(@Observes InjectionTargetFactory<T> itf) {
		logger.info("finished the scanning process");
		injectionTargetFactory++;
	}

	<T> void interceptor(@Observes Interceptor<T> i) {
		logger.info("finished the scanning process");
		interceptor++;
	}

	<T> void observerMethod(@Observes ObserverMethod<T> om) {
		logger.info("finished the scanning process");
		observerMethod++;
	}

	void passivationCapable(@Observes PassivationCapable pc) {
		logger.info("finished the scanning process");
		passivationCapable++;
	}

	public int getBeforeBeanDiscovery() {
		return beforeBeanDiscovery;
	}

	public int getProcessAnnotatedType() {
		return processAnnotatedType;
	}

	public int getAfterBeanDiscovery() {
		return afterBeanDiscovery;
	}

	public int getProcessAnnotatedTypeWithAnnotations() {
		return processAnnotatedTypeWithAnnotations;
	}

	public int getProcessBean() {
		return processBean;
	}

	public int getProcessBeanAttributes() {
		return processBeanAttributes;
	}

	public int getProcessInjectionPoint() {
		return processInjectionPoint;
	}

	public int getProcessInjectionTarget() {
		return processInjectionTarget;
	}

	public int getProcessManagedBean() {
		return processManagedBean;
	}

	public int getProcessObserverMethod() {
		return processObserverMethod;
	}

	public int getProcessProducer() {
		return processProducer;
	}

	public int getProcessProducerField() {
		return processProducerField;
	}

	public int getProcessProducerMethod() {
		return processProducerMethod;
	}

	public int getProcessSessionBean() {
		return processSessionBean;
	}

	public int getProcessSyntheticAnnotatedType() {
		return processSyntheticAnnotatedType;
	}

	public int getProducer() {
		return producer;
	}

	public int getProducerFactory() {
		return producerFactory;
	}

	public int getAfterDeploymentValidation() {
		return afterDeploymentValidation;
	}

	public int getAfterTypeDiscovery() {
		return afterTypeDiscovery;
	}

	public int getAnnotated() {
		return annotated;
	}

	public int getAnnotatedCallable() {
		return annotatedCallable;
	}

	public int getAnnotatedConstructor() {
		return annotatedConstructor;
	}

	public int getAnnotatedField() {
		return annotatedField;
	}

	public int getAnnotatedMember() {
		return annotatedMember;
	}

	public int getAnnotatedMethod() {
		return annotatedMethod;
	}

	public int getAnnotatedParameter() {
		return annotatedParameter;
	}

	public int getAnnotatedType() {
		return annotatedType;
	}

	public int getBean() {
		return bean;
	}

	public int getBeanAttributes() {
		return beanAttributes;
	}

	public int getBeanManager() {
		return beanManager;
	}

	public int getBeforeShutdown() {
		return beforeShutdown;
	}

	public int getCDIProvider() {
		return CDIProvider;
	}

	public int getDecorator() {
		return decorator;
	}

	public int getEventMetadata() {
		return eventMetadata;
	}

	public int getExtension() {
		return extension;
	}

	public int getInjectionPoint() {
		return injectionPoint;
	}

	public int getInjectionTarget() {
		return injectionTarget;
	}

	public int getInjectionTargetFactory() {
		return injectionTargetFactory;
	}

	public int getInterceptor() {
		return interceptor;
	}

	public int getObserverMethod() {
		return observerMethod;
	}

	public int getPassivationCapable() {
		return passivationCapable;
	}
}